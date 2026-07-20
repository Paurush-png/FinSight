package com.paurush.finsight.util;

import com.paurush.finsight.entity.User;
import com.paurush.finsight.exception.InvalidCredentialsException;
import com.paurush.finsight.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    private final UserRepository userRepository;

    public SecurityUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getCurrentUserEmail() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidCredentialsException("User not authenticated");
        }

        return authentication.getName();
    }

    public User getCurrentUser() {

        String email = getCurrentUserEmail();

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException("User not found"));
    }
}