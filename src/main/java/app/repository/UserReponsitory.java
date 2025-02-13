package app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entity.User;
@Repository
public interface UserReponsitory extends JpaRepository<User, UUID> {
	Optional<User> findById(UUID id);
	Optional<User> findByUsernameOrEmail(String username, String Email);
	Optional<User> findByUsername(String username);
}
