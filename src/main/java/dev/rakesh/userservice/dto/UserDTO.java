package dev.rakesh.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDTO{

	@NotEmpty
	@Size(min = 1)
	private String fullName;

	@NotEmpty
	@Size(min = 1)
	private String email;

	// TODO implement Custom Validation
	@NotEmpty
	@Size(min = 1)
	private String password;
}
