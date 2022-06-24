package com.liudi.nettychat.controller;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liudi.nettychat.dto.UserDto;
import com.liudi.nettychat.entity.User;
import com.liudi.nettychat.response.Message;
import com.liudi.nettychat.service.UserServices;
import com.liudi.nettychat.vo.MyFriendsVO;
import com.liudi.nettychat.websocket.entity.ChatMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userServices;


    @PostMapping("/registerOrLogin")
    public Message registerOrLogin(@RequestBody @Validated UserDto dto){
        return userServices.registerOrLogin(dto);
    }


    @GetMapping("/getUser")
    public User getUserById(String id, Model model){
        User user = userServices.getUserById(id);
        model.addAttribute("user",user);
        return user;
    }/**
     * 好友列表查询
     * @param userId
     * @return
     */
    @PostMapping("/myFriends")
    public Message myFriends(String userId){
        if (StringUtils.isBlank(userId)){
            return Message.fail("用户id为空");
        }
        //数据库查询好友列表
        List<MyFriendsVO> myFriends = userServices.queryMyFriends(userId);
        return Message.success(myFriends);
    }

    /**
     * 用户手机端获取未签收的消息列表
     * @param acceptUserId
     * @return
     */
    @RequestMapping("/getUnReadMsgList")
    @ResponseBody
    public Message getUnReadMsgList(String acceptUserId){
        if(StringUtils.isBlank(acceptUserId)){
            return Message.fail("接收者ID不能为空");
        }
        //根据接收ID查找为签收的消息列表
        List<com.liudi.nettychat.entity.ChatMsg> unReadMsgList = userServices.getUnReadMsgList(acceptUserId);
        return Message.success(unReadMsgList);

    }
}













