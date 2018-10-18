package com.netty.proxy.netty.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyProxyApplication.class, args);
        new NettyServer().start();
    }
}
