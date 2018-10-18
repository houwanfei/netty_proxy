package com.netty.proxy.netty.proxy.handler;

import com.netty.proxy.netty.proxy.factory.BootstrapFactory;
import com.netty.proxy.netty.proxy.init.HttpConnectChannelInitializer;
import com.netty.proxy.netty.proxy.listener.HttpChannelFutureListener;
import com.netty.proxy.netty.proxy.util.ProxyUtil;
import com.netty.proxy.netty.proxy.util.SpringContextUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Slf4j
@Component
@ChannelHandler.Sharable
public class ProxyServerHandler extends ChannelInboundHandlerAdapter {
    @Autowired
    private BootstrapFactory bootstrapFactory;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        String channelId = ProxyUtil.getChannelId(ctx);
        if (msg instanceof FullHttpRequest){
            final FullHttpRequest request = (FullHttpRequest) msg;
            InetSocketAddress address = ProxyUtil.getIpByFullRequest(request);
            //https
            if (HttpMethod.CONNECT.equals(request.method())){
                return;
            }

            //http
            log.info("代理http请求通道id{}，目标{}", channelId, request.uri());
            connect(true, address, ctx, msg);
            return;
        }
    }

    private void connect(boolean isHttp, InetSocketAddress address,
                         ChannelHandlerContext ctx, Object msg){
        ChannelFuture channelFuture;
        Bootstrap bootstrap = bootstrapFactory.build();

        if (isHttp){
            channelFuture = bootstrap.handler(new HttpConnectChannelInitializer(ctx))
                    .connect(address);

            channelFuture.addListener(new HttpChannelFutureListener(msg, ctx));
        }
    }

    /**
     * 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(",发生异常:{}", ProxyUtil.getChannelId(ctx), cause.getMessage(), cause);
        //关闭
        ctx.close();
    }
}
