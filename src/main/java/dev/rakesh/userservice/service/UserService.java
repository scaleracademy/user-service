package dev.rakesh.userservice.service;

import dev.rakesh.userservice.dto.UserDTO;
import dev.rakesh.userservice.model.User;

public interface UserService {

	public User registerUser(UserDTO userDTO);
}
