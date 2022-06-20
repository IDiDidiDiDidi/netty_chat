package com.liudi.nettychat.service.impl;

import cn.hutool.core.lang.UUID;
import com.liudi.nettychat.entity.User;
import com.liudi.nettychat.mapper.ChatMsgMapper;
import com.liudi.nettychat.mapper.UserMapper;
import com.liudi.nettychat.service.UserServices;
import com.liudi.nettychat.websocket.entity.ChatMsg;
import com.liudi.nettychat.websocket.enums.MsgSignFlagEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("userServicesImpl")
public class UserServicesImpl implements UserServices {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ChatMsgMapper chatMsgMapper;


    @Override
    public User getUserById(String id) {
        User user = userMapper.selectByPrimaryKey(id);

        return user;
    }

    @Override
    public String saveMsg(ChatMsg chatMsg) {
        com.liudi.nettychat.entity.ChatMsg msgDB = new com.liudi.nettychat.entity.ChatMsg();
        String msgId = UUID.randomUUID().toString();
        msgDB.setId(msgId);
        msgDB.setAcceptUserId(chatMsg.getReceiverId());
        msgDB.setSendUserId(chatMsg.getSenderId());
        msgDB.setCreateTime(new Date());
        msgDB.setSignFlag(MsgSignFlagEnum.unsign.type);
        msgDB.setMsg(chatMsg.getMsg());

        chatMsgMapper.insert(msgDB);

        return msgId;
    }

    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        chatMsgMapper.batchUpdateMsgSigned(msgIdList);
    }
}
