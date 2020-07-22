package dev.rakesh.userservice.service.impl;

import dev.rakesh.userservice.builder.UserPOBuilder;
import dev.rakesh.userservice.dto.ResetPasswordDTO;
import dev.rakesh.userservice.dto.UserDTO;
import dev.rakesh.userservice.event.ForgotPasswordEvent;
import dev.rakesh.userservice.event.SuccesfulRegistrationEvent;
import dev.rakesh.userservice.model.ResetPasswordToken;
import dev.rakesh.userservice.model.User;
import dev.rakesh.userservice.model.VerificationToken;
import dev.rakesh.userservice.repository.UserRepository;
import dev.rakesh.userservice.service.UserService;
import dev.rakesh.userservice.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.xml.ws.WebServiceException;
import java.util.Calendar;
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
	private ResetPasswordTokenServiceImpl resetPasswordTokenService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);


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
		if (verificationToken.getExpiryTime().getTime() < new Date().getTime()) {
			throw new WebServiceException("Token Expired");
		}

		verificationTokenService.delete(verificationToken);

		tokenUser.setActive(true);
		User savedUser = userRepository.save(tokenUser);
		return savedUser;
	}

	@Override
	public void forgotPassword(String email) {
		User user = userRepository.findByEmail(email);

		if (user == null)
			throw new WebServiceException("User Does not Exist");

		applicationEventPublisher.publishEvent(new ForgotPasswordEvent(user));
	}

	@Override
	public User resetPassword(String token, ResetPasswordDTO resetPasswordDTO) {
		ResetPasswordToken resetPasswordToken = resetPasswordTokenService.findByToken(token);

		if (resetPasswordToken == null) {
			throw new WebServiceException("Wrong Token");
		}

		User tokenUser = resetPasswordToken.getUser();

		// Validity is One Day

		if (resetPasswordToken.getExpiryTime().getTime() < new Date().getTime()) {
			throw new WebServiceException("Token Expired");
		}

		resetPasswordTokenService.delete(resetPasswordToken);
		tokenUser.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
		User savedUser = userRepository.save(tokenUser);
		return savedUser;
	}
}
