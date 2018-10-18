package com.netty.proxy.netty.proxy.init;

import com.netty.proxy.netty.proxy.handler.ProxyServerHandler;
import com.netty.proxy.netty.proxy.util.SpringContextUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ServerInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel channel) throws Exception {
        ProxyServerHandler proxyServerHandler = SpringContextUtil.getBean(ProxyServerHandler.class);
        channel.pipeline()
                .addLast("http_code_handler", new HttpRequestDecoder())
                .addLast("http_code_handler1", new HttpResponseEncoder())
                .addLast("http_aggregator", new HttpObjectAggregator(65536))
                .addLast("http_chunked", new ChunkedWriteHandler())
                .addLast("proxy_server_handler", proxyServerHandler);
    }
}
