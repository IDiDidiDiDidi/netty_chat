package com.liudi.nettychat.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liudi.nettychat.dto.UserDto;
import com.liudi.nettychat.entity.User;
import com.liudi.nettychat.mapper.ChatMsgMapper;
import com.liudi.nettychat.mapper.UserMapper;
import com.liudi.nettychat.mapper.UserMapperCustom;
import com.liudi.nettychat.response.Message;
import com.liudi.nettychat.service.UserServices;
import com.liudi.nettychat.utils.MD5Utils;
import com.liudi.nettychat.vo.MyFriendsVO;
import com.liudi.nettychat.vo.UserVo;
import com.liudi.nettychat.websocket.entity.ChatMsg;
import com.liudi.nettychat.websocket.enums.MsgSignFlagEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("userServicesImpl")
public class UserServicesImpl extends ServiceImpl<UserMapper, User> implements UserServices {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ChatMsgMapper chatMsgMapper;

    @Autowired
    UserMapperCustom userMapperCustom;


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

    @Override
    public User queryUsernameIsExit(String username) {
        return this.lambdaQuery()
                .eq(User::getUsername, username)
                .one();
    }

    @Override
    public Message registerOrLogin(UserDto dto) {
        User userResult = this.queryUsernameIsExit(dto.getUsername());
        if (userResult != null) {
            // login
            if (!userResult.getPassword().equals(MD5Utils.stringToMD5(dto.getPassword()))) {
                return Message.fail("密码不正确");
            }

            UserVo userVo = BeanUtil.copyProperties(userResult, UserVo.class);
            return Message.success(userVo);
        } else {
            // register
            String userId = this.register(dto);
            UserVo userVo = BeanUtil.copyProperties(dto, UserVo.class);
            userVo.setId(userId);
            return Message.success(userVo);
        }
    }

    @Override
    public List<MyFriendsVO> queryMyFriends(String userId) {
        return userMapperCustom.queryMyFriends(userId);
    }

    @Override
    public List<com.liudi.nettychat.entity.ChatMsg> getUnReadMsgList(String acceptUserId) {
        List<com.liudi.nettychat.entity.ChatMsg> result = chatMsgMapper.getUnReadMsgListByAcceptUid(acceptUserId);
        return result;
    }

    /**
     * 走到这一步账号肯定不存在重复情况，无需再进行判断账号是否重复
     * @param dto UserDto
     * @return boolean
     */
    private String register(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(MD5Utils.stringToMD5(dto.getPassword()));
        user.setNickname("zhang san");

        int insert = userMapper.insert(user);

        return user.getId();
    }
}
