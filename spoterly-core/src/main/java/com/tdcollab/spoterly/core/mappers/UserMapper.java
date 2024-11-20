package com.tdcollab.spoterly.core.mappers;

import com.tdcollab.spoterly.core.dtos.user.UserDto;
import com.tdcollab.spoterly.core.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto fromUserEntity(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUsername(userEntity.getUsername());
        userDto.setFirstname(userEntity.getFirstname());
        userDto.setLastname(userEntity.getLastname());
        userDto.setPassword(userEntity.getPassword());
        return userDto;
    }

    public UserEntity fromUserDto(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDto.getUsername());
        userEntity.setFirstname(userDto.getFirstname());
        userEntity.setLastname(userDto.getLastname());
        userEntity.setPassword(userDto.getPassword());
        return userEntity;
    }
}
