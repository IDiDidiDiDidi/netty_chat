package com.liudi.nettychat.service;

import com.liudi.nettychat.dto.UserDto;
import com.liudi.nettychat.entity.User;
import com.liudi.nettychat.response.Message;
import com.liudi.nettychat.vo.MyFriendsVO;
import com.liudi.nettychat.websocket.entity.ChatMsg;

import java.util.List;

public interface UserServices {

    User getUserById(String id);

    String saveMsg(ChatMsg chatMsg);

    void updateMsgSigned(List<String> msgIdList);

    User queryUsernameIsExit(String username);

    Message registerOrLogin(UserDto dto);

    List<MyFriendsVO> queryMyFriends(String userId);
}
