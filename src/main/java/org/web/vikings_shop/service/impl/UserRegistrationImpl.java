package org.web.vikings_shop.service.impl;

import org.web.vikings_shop.entities.User;
import org.web.vikings_shop.repo.UserRepo;
import org.web.vikings_shop.service.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class UserRegistrationImpl implements UserRegistration {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
   private UserRepo userRepo;
    @Override
    public User saveUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of("ROLE_USER"));
        return userRepo.save(user);
    }
}
