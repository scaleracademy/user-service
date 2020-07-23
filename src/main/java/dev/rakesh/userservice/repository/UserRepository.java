package dev.rakesh.userservice.repository;

import dev.rakesh.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String email);

	public User save(User user);

}
