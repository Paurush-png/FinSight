package com.paurush.finsight.service.impl;
import com.paurush.finsight.dto.RegisterRequest;
import com.paurush.finsight.entity.User;
import com.paurush.finsight.exception.EmailAlreadyExistsException;
import com.paurush.finsight.repository.UserRepository;
import com.paurush.finsight.service.interfaces.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User registerUser(RegisterRequest request) {
        String normalizedEmail = request.getEmail()
                .trim()
                .toLowerCase();
        // Check if the email is already registered
        if (userRepository.existsByEmail(normalizedEmail)){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        // Hash the password before storing it
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getName(), normalizedEmail, encodedPassword);
        return userRepository.save(user);
    }
}