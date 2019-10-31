package com.example.jchaos_console.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MesgHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("接受到数据");
        System.out.println("MesgHandle:" + msg);
        Thread.sleep(2 * 1000);
        channelHandlerContext.writeAndFlush("nmap");
    }
}
