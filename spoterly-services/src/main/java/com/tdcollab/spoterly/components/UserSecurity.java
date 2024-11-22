package com.tdcollab.spoterly.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserSecurity {
    public boolean isCurrentUser(String username) {
        String currentUserUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return currentUserUsername.equals(username);
    }

    public boolean isCurrentUserOrAdmin(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        return isAdmin || username.equals(authentication.getName());
    }
}
