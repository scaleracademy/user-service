package dev.naman.userservice.repository;

import dev.naman.userservice.model.ResetPasswordToken;
import dev.naman.userservice.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {
   Optional<ResetPasswordToken> findByToken(String token);
}
