package com.netty.proxy.netty.proxy.util;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class ProxyUtil {
    public static InetSocketAddress getIpByFullRequest(FullHttpRequest request){
        String[] hosts = request.headers().get("host").split(":");
        return new InetSocketAddress(hosts[0], hosts.length == 1? 80 : Integer.valueOf(hosts[1]));
    }

    public static String getChannelId(ChannelHandlerContext ctx){
        return ctx.channel().id().asShortText();
    }

    public static boolean writeAndFlush(ChannelHandlerContext ctx,Object msg, boolean isCloseOnError) {
        if(ctx.channel().isActive()){
            log.info("通道id:{},正在向客户端写入数据.",getChannelId(ctx));
            ctx.writeAndFlush(msg).addListener((ChannelFutureListener) future -> {
                if(future.isSuccess())
                    log.info("通道id:{},向客户端写入数据成功.",getChannelId(ctx));
                else
                    log.info("通道id:{},向客户端写入数据失败.e:{}",getChannelId(ctx),future.cause().getMessage(),future.cause());
            });
            return true;
        }
        else
        if (isCloseOnError)
            ctx.close();
        return false;
    }

    public static void responseFailedToClient(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_TIMEOUT));
    }
}
