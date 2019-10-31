package com.example.jchaos_console.service;

import com.example.jchaos_console.netty.NettyServerBootstrap;

import java.net.UnknownHostException;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;

public interface ControlServiceImp {


    void init() throws InterruptedException, UnknownHostException;

    void sendMesgToTerminal(Channel channel, String msg);

    Channel getChannel(ChannelId id, ChannelGroup group);

    void sendMesg(long terminalId, String msg);
    ChannelId findChannelIdFromTerminalId(long terminalId);

}
