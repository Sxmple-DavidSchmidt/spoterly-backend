package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.auth.LoginDto;
import com.tdcollab.spoterly.core.dtos.auth.RegisterDto;
import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.exceptions.*;
import com.tdcollab.spoterly.core.mappers.UserMapper;
import com.tdcollab.spoterly.core.model.AuthenticationResponse;
import com.tdcollab.spoterly.core.services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    public AuthController(AuthenticationService authenticationService, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginDto request
    ) {
        UserEntity userEntity = userMapper.entityFromLoginDto(request);
        return ResponseEntity.ok(authenticationService.authenticate(userEntity));
    }

    //TODO
    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logout() {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterDto request
    ) {
        UserEntity userEntity = userMapper.entityFromRegisterDto(request);
        return ResponseEntity.ok(authenticationService.register(userEntity));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
