package com.tdcollab.spoterly.services;

import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.exceptions.UsernameAlreadyTakenException;
import com.tdcollab.spoterly.core.model.AuthenticationResponse;
import com.tdcollab.spoterly.core.services.AuthenticationService;
import com.tdcollab.spoterly.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(PasswordEncoder passwordEncoder, JwtServiceImpl jwtService, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(UserEntity request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new UsernameAlreadyTakenException("Username \"" + request.getUsername() + "\" is already taken");

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getUsername());
        userEntity.setFirstname(request.getFirstname());
        userEntity.setLastname(request.getLastname());
        userEntity.setRole(request.getRole());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));

        UserEntity savedUser = userRepository.save(userEntity);
        String token = jwtService.generateToken(savedUser);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(UserEntity request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserEntity user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        return new AuthenticationResponse(jwtService.generateToken(user));
    }
}
