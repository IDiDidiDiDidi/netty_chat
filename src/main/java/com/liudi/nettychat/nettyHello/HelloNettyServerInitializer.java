package com.liudi.nettychat.nettyHello;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author liuD
 * @Date 2022/6/15 4:08 下午
 * @PackageName:com.liudi.ld_netty_chat.netty
 * @ClassName: HelloNettyServerInitializer
 * @Description: 初始化器， channel注册之后 就会执行其中的初始化方法
 * @Version 1.0
 */
public class HelloNettyServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 通过 socketChannel 去过的对应的管道
        ChannelPipeline pipeline = channel.pipeline();
        /**
         * 通过管道添加handler
         *
         * HttpServerCodec  是由netty自己提供的助手类，可以理解为拦截器
         * 当请求到服务器需要解码，相应到客户端需要编码
         *
          */
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        // 添加自定义助手类，给客户端渲染 hello netty
        pipeline.addLast("CustomHandler", new CustomHandler());

    }
}
