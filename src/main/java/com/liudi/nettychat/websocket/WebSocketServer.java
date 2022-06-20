package com.liudi.nettychat.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

/**
 * @Author liuD
 * @Date 2022/6/15 4:45 下午
 * @PackageName:com.liudi.ld_netty_chat.websocket
 * @ClassName: WebSocketServer
 * @Description: fuwuqi
 * @Version 1.0
 */
@Component
public class WebSocketServer {
    private static class SingletionWSServer {
        static final WebSocketServer instance = new WebSocketServer();
    }

    public static WebSocketServer getInstance() {
        return SingletionWSServer.instance;
    }

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap server;
    private ChannelFuture future;

    public WebSocketServer() {
        mainGroup = new NioEventLoopGroup();
        subGroup = new NioEventLoopGroup();
        server = new ServerBootstrap();
        server.group(mainGroup, subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WSServerInitialzer());
    }

    public void start() {
        this.future = server.bind(9999);
        if (future.isSuccess()) {
            System.out.println("启动 Netty 成果");
        }
    }

//    public static void main(String[] args) throws Exception {
//        EventLoopGroup mainGroup = new NioEventLoopGroup();
//        EventLoopGroup subGroup = new NioEventLoopGroup();
//
//        try {
//        ServerBootstrap server = new ServerBootstrap();
//        server.group(mainGroup, subGroup)
//                .channel(NioServerSocketChannel.class)
//                .childHandler(new WSServerInitialzer());
//
//            ChannelFuture future = server.bind(9999).sync();
//            future.channel().closeFuture().sync();
//        } finally {
//            mainGroup.shutdownGracefully();
//            subGroup.shutdownGracefully();
//        }
//    }
}
