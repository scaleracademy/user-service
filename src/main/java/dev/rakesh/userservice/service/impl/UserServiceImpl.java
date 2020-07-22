package dev.rakesh.userservice.service.impl;

import dev.rakesh.userservice.builder.UserPOBuilder;
import dev.rakesh.userservice.dto.UserDTO;
import dev.rakesh.userservice.event.SuccesfulRegistrationEvent;
import dev.rakesh.userservice.model.User;
import dev.rakesh.userservice.model.VerificationToken;
import dev.rakesh.userservice.repository.UserRepository;
import dev.rakesh.userservice.service.UserService;
import dev.rakesh.userservice.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.xml.ws.WebServiceException;
import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private VerificationTokenService verificationTokenService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Override
	public User registerUser(@Valid UserDTO userDTO) {
		User user = applicationContext.getBean(UserPOBuilder.class).buildPO(userDTO);

		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new WebServiceException("Email Already Exists");
		}

		User savedUser = userRepository.save(user);

		applicationEventPublisher.publishEvent(new SuccesfulRegistrationEvent(savedUser));
		return savedUser;

	}

	@Override
	public User validateUser(String token) {
		VerificationToken verificationToken = verificationTokenService.findByToken(token);

		if (verificationToken == null) {
			throw new WebServiceException("Wrong Token");
		}

		User tokenUser = verificationToken.getUser();

		// Validity is One Day

		Long Diff = new Date().getTime() - verificationToken.getExpiryTime().toInstant().toEpochMilli();
		if (Diff > 86400 * 1000) {
			throw new WebServiceException("Token Expired");
		}

		verificationTokenService.delete(verificationToken);

		tokenUser.setActive(true);
		User savedUser = userRepository.save(tokenUser);
		return savedUser;
	}
}
