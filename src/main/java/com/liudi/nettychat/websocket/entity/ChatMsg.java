package com.liudi.nettychat.websocket.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author liuD
 * @Date 2022/6/16 3:46 下午
 * @PackageName:com.liudi.ld_netty_chat.websocket
 * @ClassName: ChatMsg
 * @Description: ChatMsg
 * @Version 1.0
 */
@Data
public class ChatMsg implements Serializable {
    private String senderId; // 发送者id
    private String receiverId; // 接收者id
    private String msg; // 消息内容
    private String msgId; // 消息id,可用于消息的签收
}
