package org.web.vikings_shop.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NormalLoginSuccesshandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Get the roles of the authenticated user
        var authorities = authentication.getAuthorities();

        String redirectUrl = "/access-denied"; // Default fallback

        // Redirect based on roles
        if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
            redirectUrl = "/admin/home";
        } else if (authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
            redirectUrl = "/user/home";
        }

        // Redirect the user
        response.sendRedirect(redirectUrl);
    }
    }



