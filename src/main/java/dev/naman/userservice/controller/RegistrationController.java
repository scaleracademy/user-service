package dev.naman.userservice.controller;

import dev.naman.userservice.dto.ResponseDto;
import dev.naman.userservice.dto.UserDto;
import dev.naman.userservice.dto.UserResponseDto;
import dev.naman.userservice.model.User;
import dev.naman.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public ResponseDto<UserResponseDto> registerUser(@RequestBody UserDto userDto) {
        log.info("received registration request for " + userDto.getEmail());
        User user = userService.registerUser(userDto);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(), user.getFullName(), user.getEmail(), user.isActive())
        );
    }

    // /user/confirm?token=83677e98730803
    @GetMapping("/user/confirm")
    public ResponseDto<UserResponseDto> validateUser(@RequestParam String token) {
        User user = userService.validateUser(token);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(), user.getFullName(), user.getEmail(), user.isActive())
        );
    }
	 @PostMapping("user/resetPassword")
    public ResponseDto<UserResponseDto> resetPassword(@RequestBody UserDto userDto){
        User user=userService.resetPassword(userDto);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(),user.getFullName(),user.getEmail(),user.isActive())
        );
    }
    @PostMapping("user/newPassword")
    public ResponseDto<UserResponseDto> newPassword(@RequestParam String token,@RequestBody UserDto userDto){
        User user=userService.newPassowrd(token,userDto);
        return new ResponseDto<>(
                HttpStatus.OK,
                new UserResponseDto(user.getId(),user.getFullName(),user.getEmail(),user.isActive())

        );
    }

}
