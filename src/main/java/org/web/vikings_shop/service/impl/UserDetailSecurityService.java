package org.web.vikings_shop.service.impl;

import org.web.vikings_shop.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailSecurityService implements UserDetailsService {
   @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Logger logger = LoggerFactory.getLogger(UserDetailSecurityService.class);
        logger.info("User found with username: " + user.getUsername());
        System.out.println("User found with username: " + user.getUsername());
        return user;
    }
}
