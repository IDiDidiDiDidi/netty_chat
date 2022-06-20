package com.liudi.nettychat.websocket.entity;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONException;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @Author liuD
 * @Date 2022/6/16 3:45 下午
 * @PackageName:com.liudi.ld_netty_chat.websocket
 * @ClassName: DataContent
 * @Description: DataContent
 * @Version 1.0
 */
@Data
@ToString
public class DataContent implements Serializable {
    /**
     * 动作类型
     */
    private Integer action;
    /**
     * // chat内容
     */
    private ChatMsg chatMsg;
    /**
     * // 扩展字段
     */
    private String extand;


//    public static void main(String[] args) {
//        DataContent dataContent = new DataContent();
//        ChatMsg chatMsg = new ChatMsg();
//        //{"action":1,"chatMsg":{"senderId":"11111","receiverId":null,"msg":null,"msgId":null},"extand":null}
//        chatMsg.setSenderId("1111");
//        dataContent.setAction(1);
//        dataContent.setChatMsg(chatMsg);
//        String jsonString = JSON.toJSONString(dataContent);
//
//        System.out.println(jsonString);
//
//        String jsonStr = "{\"action\":1,\"chatMsg\":{\"senderId\":\"11111\",\"receiverId\":null,\"msg\":null,\"msgId\":null},\"extand\":null}";
//
//        DataContent parseObject = JSON.parseObject(jsonStr, DataContent.class);
//
//        DataContent dataContentBean = BeanUtil.copyProperties("{\"action\":1,\"chatMsg\":{\"senderId\":\"11111\",\"receiverId\":null,\"msg\":null,\"msgId\":null},\"extand\":null}", DataContent.class);
//        System.out.println(dataContentBean);
//
//    }
}
