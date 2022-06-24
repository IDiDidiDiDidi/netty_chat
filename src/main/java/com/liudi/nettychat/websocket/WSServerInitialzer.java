package com.liudi.nettychat.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

/**
 * @Author liuD
 * @Date 2022/6/15 4:51 下午
 * @PackageName:com.liudi.ld_netty_chat.websocket
 * @ClassName: WSServerInitialzer
 * @Description: TODO
 * @Version 1.0
 */
@Component
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();

        // websocket 基于http协议所需要的http 编解码器
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        // 在http上有一些数据流产生，有大有小，需对其处理，使用netty对大数据流读写提供支持，这个类叫：ChunkedWriteHandler
        pipeline.addLast(new ChunkedWriteHandler());
        // 对httpmsg 进行聚合处理，聚合成 request 或 respon
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        /**
         * 该handler是用于处理websocket的握手动作，
         * 它会在握手的时候把http协议升级为websocket协议
         * 如果是客户端发送的请求，则可以通过handler来处理
         * 对于websocket 来讲 都是以frames进行传输的，不同的frames有不同的用途
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 自定义的handler
        pipeline.addLast(new ChatHandler());
        //=======================================增加心跳支持=====================================================================================

        /**
         * 针对客户端，如果在1分钟时间内，没有向服务端发送心跳（all），则主动断开连接
         * 如果有读写空闲，则不做任何操作
         */
        pipeline.addLast(new IdleStateHandler(8, 10, 12));

        // 自定义空闲监测 handler
        pipeline.addLast(new HeartBeatHandler());
    }
}
