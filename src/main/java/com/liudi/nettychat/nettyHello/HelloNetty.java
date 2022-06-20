package com.liudi.nettychat.nettyHello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author liuD
 * @Date 2022/6/15 3:52 下午
 * @PackageName:com.liudi.ld_netty_chat.netty
 * @ClassName: HelloNetty
 * @Description: helly netty 实现客户端发送请求， 服务器 给予相应
 * @Version 1.0
 */
public class HelloNetty {
    public static void main(String[] args) {
        // 创建一组线程池
        // 主线程：用于接受客户端的请求，不做任何处理
        EventLoopGroup group1 = new NioEventLoopGroup();

        // 从线程：主线程会把任务交给他，让其做任务
        NioEventLoopGroup group2 = new NioEventLoopGroup();

        try {
            // 创建服务器启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(group1, group2)// 设置主从线程组
                    .channel(NioServerSocketChannel.class)//双向通道
                    .childHandler(new HelloNettyServerInitializer()); //添加子处理器， 用于处理从线程池的任务

            // 启动服务 并且设置端口号，同时启动方式为同步
            ChannelFuture future =  serverBootstrap.bind(8888).sync();

            // 监听关闭的channel，设置为同步方式
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group1.shutdownGracefully();
            group2.shutdownGracefully();
        }


    }
}
