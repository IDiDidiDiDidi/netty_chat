package com.liudi.nettychat.nettyHello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Author liuD
 * @Date 2022/6/15 4:16 下午
 * @PackageName:com.liudi.ld_netty_chat.netty
 * @ClassName: CustomHandler
 * @Description: 自定义助手类，
 * @Version 1.0
 */
public class CustomHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        // 获取channel
        Channel channel = ctx.channel();

        if (msg instanceof HttpRequest) {
            // 在控制台打印远程地址
            System.out.println("channel.remoteAddress============================" + channel.remoteAddress());
            // 定义向客户端发送的内容

            ByteBuf content = Unpooled.copiedBuffer("hello netty ~", CharsetUtil.UTF_8);
            // 构建 http response
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            // 为相应增加数据类型和长度
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            // 把相应渲染到 html 客户端页面上
            ctx.writeAndFlush(response);
        }
    }





}
