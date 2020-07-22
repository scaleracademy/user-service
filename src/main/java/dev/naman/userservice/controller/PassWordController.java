package dev.naman.userservice.controller;

import dev.naman.userservice.dto.ResponseDto;
import dev.naman.userservice.dto.UserDto;
import dev.naman.userservice.dto.UserResponseDto;
import dev.naman.userservice.model.User;
import dev.naman.userservice.service.UserService;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PassWordController {

    private final UserService userService;

    @Autowired
    public PassWordController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/resetPassWord/")
    public ResponseDto<UserResponseDto> resetPassWord(@RequestBody User registeredUser)
    {
           User user =  userService.resetPassWord(registeredUser);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(), user.getFullName(), user.getEmail(), user.isActive())
        );
    }


    @PostMapping("/user'reset/newPassword/")
    public ResponseDto<UserResponseDto> setPassword(@RequestParam String token ,@RequestParam String newPassword)
    {
           User user = userService.setPassWord(token,newPassword);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(), user.getFullName(), user.getEmail(), user.isActive())
        );
    }


}
