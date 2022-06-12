package es.dws.quidditch.repository;

import es.dws.quidditch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByName(String name);
    Optional<User> deleteUserByName(String name);
}
