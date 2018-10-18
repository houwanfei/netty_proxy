package com.netty.proxy.netty.proxy.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyServerOutboundHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise channelPromise) throws Exception {
        super.close(ctx, channelPromise);
        log.info("[代理服务器输出事件处理类]通道id:{}关闭通道");
    }
}
