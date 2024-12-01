package org.web.vikings_shop.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.web.vikings_shop.entities.User;
import org.web.vikings_shop.repo.UserRepo;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AuthenticationSuccessHandlerOauth2 implements AuthenticationSuccessHandler {
    private final UserRepo userRepo;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public AuthenticationSuccessHandlerOauth2(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

        Logger logger = LoggerFactory.getLogger(AuthenticationSuccessHandlerOauth2.class);

        logger.info(user.getAttribute("name"));
        logger.info(user.getAttribute("email"));


        String name = Objects.requireNonNull(user.getAttribute("name")).toString();
        String email = Objects.requireNonNull(user.getAttribute("email")).toString();



        User user1 = new User();
        user1.setName(name);
        user1.setEmail(email);
        user1.setPhone("981234567");
        user1.setId(UUID.randomUUID().toString());
        user1.setPassword(passwordEncoder.encode("password"));
        user1.setGender("Male");
        user1.setAddress("Nepal");
        user1.setRoles(Set.of("ROLE_USER"));


        User user2 =  userRepo.findByEmail(email).orElse(null);
        if(user2 == null) {
            userRepo.save(user1);

        }



        Set<GrantedAuthority> updatedAuthorities = user1.getRoles().stream()
                .map(role -> (GrantedAuthority) () -> role)
                .collect(Collectors.toSet());

        // Create a new authentication token with updated authorities
        Authentication updatedAuth = new OAuth2AuthenticationToken(
                (OAuth2User) authentication.getPrincipal(),
                updatedAuthorities,
                ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId()
        );
        SecurityContextHolder.getContext().setAuthentication(updatedAuth);

        // Redirect after successful authentication
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/userhome");
    }


    }

