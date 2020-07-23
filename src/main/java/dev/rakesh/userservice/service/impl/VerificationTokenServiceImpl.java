package dev.rakesh.userservice.service.impl;

import dev.rakesh.userservice.model.VerificationToken;
import dev.rakesh.userservice.repository.VerificationTokenRepository;
import dev.rakesh.userservice.service.VerificationTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

	private static final Logger logger = LoggerFactory.getLogger(VerificationTokenServiceImpl.class);

	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Override
	public void save(VerificationToken verificationToken) {
		verificationTokenRepository.save(verificationToken);
	}

	@Override
	public VerificationToken findByToken(String token) {
		return verificationTokenRepository.findByToken(token);
	}

	@Override
	public void delete(VerificationToken verificationToken) {
		verificationTokenRepository.delete(verificationToken);
	}


}
