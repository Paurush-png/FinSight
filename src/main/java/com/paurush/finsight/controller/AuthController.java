package com.paurush.finsight.controller;

import com.paurush.finsight.dto.RegisterRequest;
import com.paurush.finsight.entity.User;
import com.paurush.finsight.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.paurush.finsight.dto.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService) {
        this.userService = userService;
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
}
