package com.liudi.nettychat.controller;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.liudi.nettychat.entity.User;
import com.liudi.nettychat.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServices userServices;

    @GetMapping("/getUser")
    public User getUserById(String id, Model model){
        User user = userServices.getUserById(id);
        model.addAttribute("user",user);
        return user;
    }
}













