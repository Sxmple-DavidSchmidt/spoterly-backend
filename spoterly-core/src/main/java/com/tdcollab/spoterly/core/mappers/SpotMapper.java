package com.tdcollab.spoterly.core.mappers;

import com.tdcollab.spoterly.core.dtos.spot.CreateSpotDto;
import com.tdcollab.spoterly.core.dtos.spot.MinimalSpotDto;
import com.tdcollab.spoterly.core.dtos.spot.SpotDto;
import com.tdcollab.spoterly.core.dtos.user.UserDto;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import org.springframework.stereotype.Component;

@Component
public class SpotMapper {
    private final UserMapper userMapper;

    public SpotMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public SpotDto fromSpotEntity(SpotEntity spotEntity) {
        UserDto userDto = userMapper.fromUserEntity(spotEntity.getAuthor());

        SpotDto spotDto = new SpotDto();
        spotDto.setId(spotEntity.getId());
        spotDto.setAuthor(userDto);
        spotDto.setName(spotEntity.getName());
        spotDto.setDescription(spotEntity.getDescription());
        spotDto.setLongitude(spotEntity.getLatitude());
        spotDto.setLongitude(spotEntity.getLongitude());
        return spotDto;
    }

    public MinimalSpotDto minimalFromSpotEntity(SpotEntity spotEntity) {
        MinimalSpotDto minimalSpotDto = new MinimalSpotDto();
        minimalSpotDto.setId(spotEntity.getId());
        minimalSpotDto.setAuthor_id(spotEntity.getAuthor().getUsername());
        minimalSpotDto.setName(spotEntity.getName());
        minimalSpotDto.setDescription(spotEntity.getDescription());
        minimalSpotDto.setLatitude(spotEntity.getLatitude());
        minimalSpotDto.setLongitude(spotEntity.getLongitude());
        return minimalSpotDto;
    }

    public SpotEntity entityFromCreateSpotDto(CreateSpotDto createSpotDto) {
        SpotEntity spotEntity = new SpotEntity();
        spotEntity.setName(createSpotDto.getName());
        spotEntity.setDescription(createSpotDto.getDescription());
        spotEntity.setLatitude(createSpotDto.getLatitude());
        spotEntity.setLongitude(createSpotDto.getLongitude());
        return spotEntity;
    }
}
