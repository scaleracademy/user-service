package dev.rakesh.userservice.repository;

import dev.rakesh.userservice.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	public VerificationToken save(VerificationToken verificationToken);

	public VerificationToken findByToken(String token);

	public void delete(VerificationToken verificationToken);
}
