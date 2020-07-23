package dev.rakesh.userservice.service.impl;

import dev.rakesh.userservice.model.ResetPasswordToken;
import dev.rakesh.userservice.repository.ResetPasswordTokenRepository;
import dev.rakesh.userservice.service.ResetPasswordTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

	private static final Logger logger = LoggerFactory.getLogger(VerificationTokenServiceImpl.class);

	@Autowired
	private ResetPasswordTokenRepository resetPasswordTokenRepository;

	@Override
	public void save(ResetPasswordToken resetPasswordToken) {
		resetPasswordTokenRepository.save(resetPasswordToken);
	}

	@Override
	public ResetPasswordToken findByToken(String token) {
		return resetPasswordTokenRepository.findByToken(token);

	}

	@Override
	public void delete(ResetPasswordToken resetPasswordToken) {
		resetPasswordTokenRepository.delete(resetPasswordToken);
	}
}
