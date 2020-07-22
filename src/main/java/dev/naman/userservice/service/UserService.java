package dev.naman.userservice.service;

import dev.naman.userservice.dto.UserDto;
import dev.naman.userservice.model.User;

public interface UserService {

    User registerUser(UserDto userDto);

    User validateUser(String token);

    User resetPassWord(User User);

    User setPassWord(String token, String newPassword);

}
