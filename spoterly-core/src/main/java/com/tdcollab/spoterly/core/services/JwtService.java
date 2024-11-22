package com.tdcollab.spoterly.core.services;

import com.tdcollab.spoterly.core.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Date;

public interface JwtService {
        String generateToken(UserEntity user);
        String extractUsername(String token);
        Date extractExpiration(String token);
        boolean isExpired(String token);
        boolean isValid(String token, UserDetails user);
}
