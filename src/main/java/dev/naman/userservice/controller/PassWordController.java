package dev.naman.userservice.controller;

import dev.naman.userservice.model.User;
import dev.naman.userservice.service.UserService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PassWordController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/resetPassWord/")
    public void resetPassWord(@RequestBody User user)
    {

    }

    @PostMapping("/user'reset/newPassword/")
    public void setPassword(@RequestParam String newPassword)
    {

    }





}
