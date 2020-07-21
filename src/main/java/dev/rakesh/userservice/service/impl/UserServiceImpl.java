package dev.rakesh.userservice.service.impl;

import dev.rakesh.userservice.builder.UserPOBuilder;
import dev.rakesh.userservice.dto.UserDTO;
import dev.rakesh.userservice.model.User;
import dev.rakesh.userservice.repository.UserRepository;
import dev.rakesh.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.ws.WebServiceException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private UserRepository userRepository;

	@Override
	public User registerUser(UserDTO userDTO) {
		User user = applicationContext.getBean(UserPOBuilder.class).buildPO(userDTO);

		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new WebServiceException("Email Already Exists");
		}

		User savedUser = userRepository.save(user);
		return savedUser;

	}
}
