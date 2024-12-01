package org.web.vikings_shop.repo;

import org.web.vikings_shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
   Optional<User> findByEmail(String email);

}