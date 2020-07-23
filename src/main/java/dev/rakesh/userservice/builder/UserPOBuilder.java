package dev.rakesh.userservice.builder;

import dev.rakesh.userservice.dto.UserDTO;
import dev.rakesh.userservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserPOBuilder {

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

	public User user;

	public User buildPO(UserDTO userDTO) {
		user = new User();

		user.setEmail(userDTO.getEmail());
		user.setFullName(userDTO.getFullName());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		user.setActive(false);
		return user;
	}
}
