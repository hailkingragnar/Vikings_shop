package org.web.vikings_shop.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public String getLoggedInUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            // Check if the principal is of type OAuth2User (i.e., user authenticated via OAuth)
            if (principal instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) principal;

                // Get email from OAuth2User attributes (Assuming the attribute name for email is "email")
                return oauth2User.getAttribute("email");
            }
            // If it's not OAuth2User, fallback to UserDetails (for non-OAuth authentication)
            else if (principal instanceof UserDetails) {
                return ((UserDetails) principal).getUsername(); // Assuming email is used as the username
            } else {
                return principal.toString();
            }
        }

        throw new IllegalStateException("User is not authenticated");
    }
}
