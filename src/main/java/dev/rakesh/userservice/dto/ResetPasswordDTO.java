package dev.rakesh.userservice.dto;

import dev.rakesh.userservice.annotations.ValidPassword;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ResetPasswordDTO {

	@ValidPassword
	@NotEmpty(message = "Password should not be empty")
	@Size(min = 6, message = "Password Length should be greater than 6")
	private String password;
}
