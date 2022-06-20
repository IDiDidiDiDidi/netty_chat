package com.liudi.nettychat.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author liuD
 * @Date 2022/6/20 9:44 上午
 * @PackageName:com.liudi.nettychat.entity
 * @ClassName: ChatMsg
 * @Description: TODO
 * @Version 1.0
 */
@Data
@TableName("chat_msg")
public class ChatMsg {
    private String id;

    private String sendUserId;

    private String acceptUserId;

    private String msg;

    private Integer signFlag;

    private Date createTime;
}
