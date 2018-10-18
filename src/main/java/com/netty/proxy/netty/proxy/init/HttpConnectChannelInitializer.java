package com.netty.proxy.netty.proxy.init;

import com.netty.proxy.netty.proxy.handler.HttpConnectHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpConnectChannelInitializer extends ChannelInitializer {
    private ChannelHandlerContext ctx;

    public HttpConnectChannelInitializer(ChannelHandlerContext ctx){
        this.ctx = ctx;
    }
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline()
                .addLast(new HttpClientCodec())
                .addLast(new HttpObjectAggregator(65536))
                .addLast(new ChunkedWriteHandler())
                .addLast(new HttpConnectHandler(ctx));
    }
}
