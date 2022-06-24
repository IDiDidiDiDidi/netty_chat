package com.liudi.nettychat.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author liuD
 * @Date 2022/6/23 4:52 下午
 * @PackageName:com.liudi.nettychat.websocket
 * @ClassName: HeartBeatHandler
 * @Description: 用户监测channel的心跳 handler
 * @Version 1.0
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            // 强制类型转换
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("进入读空闲。。。。。。");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("进入writer空闲。。。。。。");
            } else if (event.state() == IdleState.ALL_IDLE) {
                System.out.println("channel 关闭之前： users的数量为：" + ChatHandler.users.size());
                Channel channel = ctx.channel();
                // 资源释放
                channel.close();
                System.out.println("channel 关闭之后： users的数量为：" + ChatHandler.users.size());
            }
        }

    }
}
