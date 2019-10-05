package com.zero.learn_spring_boot.web;

import com.zero.learn_spring_boot.model.User;
import com.zero.learn_spring_boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public Object test(){
        User user = new User();
        user.setAge(18);
        user.setName("陈小兵");
        try {
            userService.addUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return user.toString();
    }
}
