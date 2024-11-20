package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.auth.LoginDto;
import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginRequest) {
        UserEntity user = userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        if (user != null) {
            return ResponseEntity.ok(new AuthResponse(true, "Authenticated successfully", user));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponse(false, "Invalid credentials", null));
        }
    }

    @Data
    @AllArgsConstructor
    static
    class AuthResponse {
        private boolean authenticated;
        private String message;
        private UserEntity user;
    }
}
