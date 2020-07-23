package dev.rakesh.userservice.service;

import dev.rakesh.userservice.model.ResetPasswordToken;

public interface ResetPasswordTokenService {
	void save(ResetPasswordToken resetPasswordToken);

	ResetPasswordToken findByToken(String token);

	void delete(ResetPasswordToken resetPasswordToken);
}
