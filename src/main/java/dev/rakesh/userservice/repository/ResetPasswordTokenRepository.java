package dev.rakesh.userservice.repository;

import dev.rakesh.userservice.model.ResetPasswordToken;
import dev.rakesh.userservice.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {

	public ResetPasswordToken save(ResetPasswordToken resetPasswordToken);

	public ResetPasswordToken findByToken(String token);

	public void delete(ResetPasswordToken resetPasswordToken);
}
