package com.netty.proxy.netty.proxy;

import com.netty.proxy.netty.proxy.init.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {
    int port = 8090;
    public void start(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(5);
        EventLoopGroup workGroup = new NioEventLoopGroup(5);

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ServerInitializer())
                .option(ChannelOption.SO_BACKLOG, 2048)
                .option(ChannelOption.SO_RCVBUF, 1024 * 1024);

        log.info("代理服务器在端口{}启动", port);
        try {
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
            bossGroup.shutdownGracefully().sync();
            workGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            log.info("中断异常", e);
        } finally {

        }
    }
}
