package dev.rakesh.userservice.service;

import dev.rakesh.userservice.model.VerificationToken;

public interface VerificationTokenService {

	void save(VerificationToken verificationToken);

	VerificationToken findByToken(String verificationToken);

	void delete(VerificationToken verificationToken);

}
