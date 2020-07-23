package dev.rakesh.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

	private Long id;

	private String fullName;

	private String email;

	private boolean isActive;

	public static class UserBuilder {

		private Long id;
		private String fullName;
		private String email;
		private boolean isActive;

		public UserBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public UserBuilder setFullName(String fullName) {
			this.fullName = fullName;
			return this;
		}

		public UserBuilder setEmail(String email) {
			this.email = email;
			return this;
		}

		public UserBuilder setIsActive(boolean isActive) {
			this.isActive = isActive;
			return this;
		}

		public UserResponseDTO getUser(UserBuilder userBuilder) {
			UserResponseDTO userResponseDTO = new UserResponseDTO();

			userResponseDTO.setEmail(userBuilder.email);
			userResponseDTO.setFullName(userBuilder.fullName);
			userResponseDTO.setId(userBuilder.id);
			userResponseDTO.setActive(userBuilder.isActive);

			return userResponseDTO;
		}
	}
}
