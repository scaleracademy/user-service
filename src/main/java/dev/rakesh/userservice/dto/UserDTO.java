package dev.rakesh.userservice.dto;

import dev.rakesh.userservice.annotations.ValidEmail;
import dev.rakesh.userservice.annotations.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDTO{

	@NotEmpty(message = "fullName should not be empty")
	@Size(min = 1)
	private String fullName;

	@ValidEmail
	@NotEmpty(message = "Email should not be empty")
	@Size(min = 1)
	private String email;

	@ValidPassword
	@NotEmpty(message = "Password should not be empty")
	@Size(min = 6, message = "Password Length should be greater than 6")
	private String password;
}
