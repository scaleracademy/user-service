package dev.rakesh.userservice.service;

import dev.rakesh.userservice.dto.ResetPasswordDTO;
import dev.rakesh.userservice.dto.UserDTO;
import dev.rakesh.userservice.model.User;

public interface UserService {

	public User registerUser(UserDTO userDTO);

	public User validateUser(String token);

	public void forgotPassword(String email);

	public User resetPassword(String token, ResetPasswordDTO passwordDTO);
}
