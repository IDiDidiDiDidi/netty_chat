package com.liudi.nettychat.websocket;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liudi.nettychat.service.UserServices;
import com.liudi.nettychat.utils.SpringUtils;
import com.liudi.nettychat.websocket.entity.ChatMsg;
import com.liudi.nettychat.websocket.entity.DataContent;
import com.liudi.nettychat.websocket.enums.MsgActionEnum;
import com.liudi.nettychat.utils.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author liuD
 * @Date 2022/6/15 5:09 下午
 * @PackageName:com.liudi.ld_netty_chat.websocket
 * @ClassName: ChatHander
 * @Description: 处理 msg
 * 由于他的传输数据的载体是frame，这个frame，在netty中，适用于为websocket专门处理文本对象的，frame是消息的载体，
 * @Version 1.0
 */
@Slf4j
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
//
//    @Autowired
//    private UserServices userServices;
//
//    private static ChatHandler chatHandler;
//
//    @PostConstruct
//    public void init() {
//        chatHandler = this;
//    }

    /**
     * 用于记录和管理所有客户端的管道
     */
    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 1.获取客户端所传输的消息
        String content = msg.text();
        // 转成实体形式
        DataContent dataContent = JSON.parseObject(content, DataContent.class);
        Integer action = dataContent.getAction();

        // 获取该netty的 通道
        Channel channel = ctx.channel();


        /**
         * 2.判断消息类型，根据不用类型处理不同业务
         */
        if (MsgActionEnum.CONNECT.type.equals(action)) {
            // 2.1 当websocket第一次open时，初始化channel，把channel与userId关联起来
            String senderId = dataContent.getChatMsg().getSenderId();
            UserChannelRel.put(senderId, channel);

            // test
            for (Channel c : users) {
                System.out.println("================" + c.id().asLongText());
            }
        }else if (MsgActionEnum.CHAT.type.equals(action)) {
            // 2.2 聊天类型的消息，把聊天记录保存到数据库中，同时标记消息的签收状态【未签收】
            ChatMsg chatMsg = dataContent.getChatMsg();
            String msgContent = chatMsg.getMsg();
            String senderId = chatMsg.getSenderId();

            // 接收方id
            String receiverId = chatMsg.getReceiverId();
            // 保存消息到数据库，并且标记为【未签收】
//            UserServices userServices = (UserServices) SpringUtil.getBean("userServicesImpl");

            UserServices userServices = SpringUtils.getBean(UserServices.class);

            String msgId = userServices.saveMsg(chatMsg);
            chatMsg.setMsgId(msgId);

            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMsg(chatMsg);

            // 发送消息
            Channel receiverChannel = UserChannelRel.get(receiverId);
            if (receiverChannel == null) {
                // 离线用户
            } else {
                // 不为null 时，去channelGroup 去查找对应的channel 是否存在
                Channel findChannel = users.find(receiverChannel.id());
                if (findChannel != null) {
                    receiverChannel.writeAndFlush(
                            new TextWebSocketFrame(
                                    String.valueOf(dataContentMsg)
                            )
                    );
                } else {
                    // 离线用户
                }
            }
        }else if (MsgActionEnum.SIGNED.type.equals(action)) {
            // 2.3 签收消息类型，针对具体消息进行签收，修改数据库中对应消息的签收状态【已签收】
            UserServices userServices = (UserServices) SpringUtil.getBean("userServicesImpl");
            //扩展字段在signed 类型消息中 ，代表需要去签收的消息id，逗号间隔
            String msgIdsStr = dataContent.getExtand();
            String[] msgsId = msgIdsStr.split(",");

            List<String> msgIdList = new ArrayList<>();
            for (String mid: msgsId) {
                if(StringUtils.isNotBlank(mid)){
                    msgIdList.add(mid);
                }
            }
            System.out.println(msgIdList.toString());
            if(msgIdList!=null && !msgIdList.isEmpty() && msgIdList.size()>0){
                //批量签收
                userServices.updateMsgSigned(msgIdList);
            }
        }else if (MsgActionEnum.KEEPALIVE.type.equals(action)) {
            // 2.3 心跳类型的消息
            System.out.println("收到来自channel 为【"+channel+"】的心跳包");
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String chanelId = ctx.channel().id().asShortText();
        System.out.println("客户端被移除：channel id 为："+chanelId);

        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 当出现异常就关闭连接, 同事从ChannelGroup中移除
        ctx.close();
        users.remove(ctx.channel());
    }
}
