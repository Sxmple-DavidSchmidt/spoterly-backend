package com.tdcollab.spoterly.core.mappers;

import com.tdcollab.spoterly.core.dtos.spot.CreateSpotDto;
import com.tdcollab.spoterly.core.dtos.spot.DeleteSpotDto;
import com.tdcollab.spoterly.core.dtos.spot.SpotDto;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.services.SpotService;
import org.springframework.stereotype.Component;

@Component
public class SpotMapper {
    private final SpotService spotService;

    public SpotMapper(SpotService spotService) {
        this.spotService = spotService;
    }

    public SpotDto fromSpotEntity(SpotEntity spotEntity) {
        SpotDto spotDto = new SpotDto();
        spotDto.setId(spotEntity.getId());
        spotDto.setName(spotEntity.getName());
        spotDto.setDescription(spotEntity.getDescription());
        spotDto.setLongitude(spotEntity.getLatitude());
        spotDto.setLongitude(spotEntity.getLongitude());
        return spotDto;
    }

    public SpotEntity fromSpotDto(SpotDto spotDto) {
        SpotEntity spotEntity = new SpotEntity();
        spotEntity.setId(spotDto.getId());
        spotEntity.setName(spotDto.getName());
        spotEntity.setDescription(spotDto.getDescription());
        spotEntity.setLongitude(spotDto.getLatitude());
        spotEntity.setLongitude(spotDto.getLongitude());
        return spotEntity;
    }

    public SpotEntity entityFromCreateSpotDto(CreateSpotDto createSpotDto) {
        SpotEntity spotEntity = new SpotEntity();
        // id is generated in constructor
        spotEntity.setName(createSpotDto.getName());
        spotEntity.setDescription(createSpotDto.getDescription());
        spotEntity.setLatitude(createSpotDto.getLatitude());
        spotEntity.setLongitude(createSpotDto.getLongitude());
        return spotEntity;
    }

    public SpotDto entityFromDeleteSpotDto(DeleteSpotDto deleteSpotDto) {
        SpotEntity spotEntity = spotService.findById(deleteSpotDto.getId());
        return fromSpotEntity(spotEntity);
    }
}
