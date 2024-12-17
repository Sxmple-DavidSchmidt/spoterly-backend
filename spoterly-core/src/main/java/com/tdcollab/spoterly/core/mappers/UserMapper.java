package com.tdcollab.spoterly.core.mappers;

import com.tdcollab.spoterly.core.dtos.auth.LoginDto;
import com.tdcollab.spoterly.core.dtos.auth.RegisterDto;
import com.tdcollab.spoterly.core.dtos.user.MinimalUserDto;
import com.tdcollab.spoterly.core.entities.Role;
import com.tdcollab.spoterly.core.entities.UserEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {
    public MinimalUserDto minimalFromUserEntity(UserEntity userEntity) {
        UUID profilePictureId = userEntity.getProfilePicture() == null ? null : userEntity.getProfilePicture().getId();
        MinimalUserDto minimalUserDto = new MinimalUserDto();
        minimalUserDto.setProfilePictureId(profilePictureId);
        minimalUserDto.setUsername(userEntity.getUsername());
        minimalUserDto.setFirstname(userEntity.getFirstname());
        minimalUserDto.setLastname(userEntity.getLastname());
        minimalUserDto.setRole(userEntity.getRole());
        return minimalUserDto;
    }

    public UserEntity entityFromLoginDto(LoginDto loginDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(loginDto.getUsername());
        userEntity.setPassword(loginDto.getPassword());
        return userEntity;
    }

    public UserEntity entityFromRegisterDto(RegisterDto registerDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(registerDto.getUsername());
        userEntity.setFirstname(registerDto.getFirstname());
        userEntity.setLastname(registerDto.getLastname());
        userEntity.setPassword(registerDto.getPassword());
        userEntity.setRole(Role.USER);
        return userEntity;
    }
}
