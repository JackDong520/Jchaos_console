package com.example.jchaos_console.netty.Handler;

import java.security.Principal;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Created by jackdong on 2019-10-28
 * 实现对客户端的链接，使用group进行分组
 */
public class MesgHandler extends SimpleChannelInboundHandler<String> {


    private static ChannelGroup channelGroup;
    private ChannelId SingleId;

    public MesgHandler(ChannelGroup channelGroup) {
        this.channelGroup = channelGroup;
        SingleId = null;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("接受到数据");
        System.out.println("MesgHandle channelRead0 : this msg");
        System.out.println("MesgHandle:" + msg);
        ctx.writeAndFlush("nmap");
        System.out.println("输出");
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        if (!channelGroup.contains(ctx.channel())) {
            channelGroup.add(ctx.channel());
            SingleId = ctx.channel().id();
            System.out.println("new socket added");
        }
        System.out.println("not joined new socket added");
    }

    public  ChannelId getSingleId() {
        return SingleId;
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }
}
