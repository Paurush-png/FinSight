package com.paurush.finsight.controller;

import com.paurush.finsight.dto.LoginRequest;
import com.paurush.finsight.dto.LoginResponse;
import com.paurush.finsight.dto.RegisterRequest;
import com.paurush.finsight.dto.RegisterResponse;
import com.paurush.finsight.entity.User;
import com.paurush.finsight.security.JwtService;
import com.paurush.finsight.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService,
                          JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {

        User user = userService.registerUser(request);

        RegisterResponse response = new RegisterResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                "User registered successfully"
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {

        User user = userService.loginUser(request);

        String token = jwtService.generateToken(user);

        LoginResponse response = new LoginResponse(
                "Login successful",
                token
        );

        return ResponseEntity.ok(response);
    }
}