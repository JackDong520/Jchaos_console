package com.example.jchaos_console.service;

import com.example.jchaos_console.Config.Config;
import com.example.jchaos_console.netty.NettyServerBootstrap;
import com.example.jchaos_console.netty.chat.Client;

import java.net.UnknownHostException;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;

public class ControlService implements ControlServiceImp {
   private NettyServerBootstrap bootstrap;
    private Channel channel;



    public ControlService() throws InterruptedException, UnknownHostException {
        init();
    }

    @Override
    public void init() throws InterruptedException, UnknownHostException {
        bootstrap = new NettyServerBootstrap(Config.NettyPort);
    }

    @Override
    public void sendMesgToTerminal(Channel channel, String msg) {
        channel.writeAndFlush(msg);
    }

    @Override
    public Channel getChannel(ChannelId id, ChannelGroup group) {
        return group.find(id);
    }

    @Override
    public void sendMesg(long terminalId, String msg) {

        Channel channel = getChannel(bootstrap.getMesgHandler().getSingleId(), bootstrap.getChannelGroup());
        sendMesgToTerminal(channel, msg);
    }

    @Override
    public ChannelId findChannelIdFromTerminalId(long terminalId) {
        return null;
    }

    public Channel getChannel() {
        return channel;
    }
    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        ControlService controlService = new ControlService();
        controlService.sendMesg(123,"wdnmd");
    }


}
