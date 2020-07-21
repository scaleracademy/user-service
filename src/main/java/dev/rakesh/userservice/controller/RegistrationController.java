package dev.rakesh.userservice.controller;

import dev.rakesh.userservice.dto.BaseResponseDTO;
import dev.rakesh.userservice.dto.UserDTO;
import dev.rakesh.userservice.dto.UserResponseDTO;
import dev.rakesh.userservice.model.User;
import dev.rakesh.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

	private static final Logger logger = LoggerFactory.getLogger(RestController.class);

	@Autowired
	private UserService userService;

	@PostMapping("/user/register")
	public BaseResponseDTO<UserResponseDTO> registerUser(@RequestBody UserDTO userDTO) {
		logger.info("User Registering" + userDTO.toString());

		try {
			User user = userService.registerUser(userDTO);
			UserResponseDTO.UserBuilder userBuilder = new UserResponseDTO.UserBuilder().
					setEmail(user.getEmail()).
					setFullName(user.getFullName()).
					setIsActive(user.isActive()).setId(user.getId());
			return new BaseResponseDTO<>(
					HttpStatus.OK,
					userBuilder.getUser(userBuilder)
			);
		} catch (Exception ex) {
			logger.error("Error while Registering" + ex.getMessage());
			return new BaseResponseDTO<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
