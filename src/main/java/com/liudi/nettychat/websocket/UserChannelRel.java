package com.liudi.nettychat.websocket;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author liuD
 * @Date 2022/6/16 4:00 下午
 * @PackageName:com.liudi.ld_netty_chat.websocket
 * @ClassName: UserChannelRel
 * @Description: 用户id 和 channel的关联关系
 * @Version 1.0
 */
public class UserChannelRel {

    private static HashMap<String, Channel> manage = new HashMap<>();

    public static void put(String senderId, Channel channel) {
        manage.put(senderId, channel);
    }

    public static Channel get(String senderId) {
        return manage.get(senderId);
    }

    public static void output() {
        for (Map.Entry<String, Channel> entry : manage.entrySet()) {
            System.out.println("userId: " + entry.getKey()
                    + ", ChannelId: " + entry.getValue().id().asLongText()
            );
        }

    }
}
