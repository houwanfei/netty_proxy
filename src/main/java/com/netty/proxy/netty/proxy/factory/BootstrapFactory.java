package com.netty.proxy.netty.proxy.factory;

import com.netty.proxy.netty.proxy.config.ProxyConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BootstrapFactory {
    private final Bootstrap bootstrap;

    @Autowired
    public BootstrapFactory (ProxyConfig proxyConfig) {
        this.bootstrap = new Bootstrap()
                .group(new NioEventLoopGroup(proxyConfig.getSocket().getEventThreadNum()))
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, proxyConfig.getSocket().getConnectTimeoutMillis());
    }

    public Bootstrap build() {
        return bootstrap.clone();
    }
}
