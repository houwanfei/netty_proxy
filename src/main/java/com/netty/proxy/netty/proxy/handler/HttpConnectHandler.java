package com.netty.proxy.netty.proxy.handler;

import com.netty.proxy.netty.proxy.util.ProxyUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpConnectHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext ctx;

    public HttpConnectHandler(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }

    public void channelRead(ChannelHandlerContext ctx0, Object msg) throws Exception{
        log.info("读取到目标服务器响应");
        ProxyUtil.writeAndFlush(ctx, msg, true);
    }

}
