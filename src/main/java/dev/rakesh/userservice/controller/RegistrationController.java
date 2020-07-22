package dev.rakesh.userservice.controller;

import dev.rakesh.userservice.dto.BaseResponseDTO;
import dev.rakesh.userservice.dto.ResetPasswordDTO;
import dev.rakesh.userservice.dto.UserDTO;
import dev.rakesh.userservice.dto.UserResponseDTO;
import dev.rakesh.userservice.model.User;
import dev.rakesh.userservice.service.UserService;
import dev.rakesh.userservice.service.VerificationTokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.xml.ws.WebServiceException;

@RestController
public class RegistrationController {

	//TODO implement Mail Sender
	private static final Logger logger = LoggerFactory.getLogger(RestController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private VerificationTokenService verificationTokenService;

	@PostMapping("/user/register")
	public BaseResponseDTO<UserResponseDTO> registerUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
		logger.info("User Registering" + userDTO.toString());

		try {

			if (bindingResult.hasErrors())
				throw new WebServiceException(bindingResult.getAllErrors().get(0).getDefaultMessage());

			User user = userService.registerUser(userDTO);
			UserResponseDTO.UserBuilder userBuilder = new UserResponseDTO.UserBuilder().
																			setEmail(user.getEmail()).
																			setFullName(user.getFullName()).
																			setIsActive(user.isActive()).
																			setId(user.getId());

			return new BaseResponseDTO<>(
										HttpStatus.OK,
										userBuilder.getUser(userBuilder)
										);
		} catch (Exception ex) {
			logger.error("Error while Registering : " + ex.getMessage());
			return new BaseResponseDTO<>(ex, HttpStatus.BAD_REQUEST);
		}
	}


	@GetMapping("/user/verify")
	public BaseResponseDTO<UserResponseDTO> verifyUser(@RequestParam String token) {

		try{
			User user = userService.validateUser(token);

			UserResponseDTO.UserBuilder userBuilder = new UserResponseDTO.UserBuilder().
																	setEmail(user.getEmail()).
																	setFullName(user.getFullName()).
																	setIsActive(user.isActive()).
																	setId(user.getId());
			return new BaseResponseDTO<>(
								HttpStatus.OK,
								userBuilder.getUser(userBuilder)
			);

		} catch (Exception ex) {
			logger.error("Error while Verifying" + ex.getMessage());
			return new BaseResponseDTO<>(ex, HttpStatus.UNAUTHORIZED);
		}
	}

	@GetMapping("/user/forgot-password")
	public BaseResponseDTO<String> forgotPassword(@RequestParam String email) {

		try {
			userService.forgotPassword(email);

			return new BaseResponseDTO<>(
					HttpStatus.OK,
					"Token Generated"
			);
		} catch (Exception ex) {
			return new BaseResponseDTO<>(ex, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("user/reset-password")
	public BaseResponseDTO<UserResponseDTO> resetPassword(@RequestParam String token,
														  @RequestBody @Valid ResetPasswordDTO resetPasswordDTO,
														  BindingResult bindingResult) {
		try {

			if (bindingResult.hasErrors())
				throw new WebServiceException(bindingResult.getAllErrors().get(0).getDefaultMessage());

			User user = userService.resetPassword(token, resetPasswordDTO);
			UserResponseDTO.UserBuilder userBuilder = new UserResponseDTO.UserBuilder().
																			setEmail(user.getEmail()).
																			setFullName(user.getFullName()).
																			setIsActive(user.isActive()).
																			setId(user.getId());

			return new BaseResponseDTO<>(
					HttpStatus.OK,
					userBuilder.getUser(userBuilder)
			);

		} catch (Exception ex) {
			return new BaseResponseDTO<>(ex, HttpStatus.BAD_REQUEST);
		}
	}
}
