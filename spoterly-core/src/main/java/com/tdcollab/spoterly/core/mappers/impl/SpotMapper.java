package com.tdcollab.spoterly.core.mappers.impl;

import com.tdcollab.spoterly.core.dtos.CreateSpotDto;
import com.tdcollab.spoterly.core.dtos.SpotDto;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SpotMapper implements Mapper<SpotEntity, CreateSpotDto> {
    private final ModelMapper modelMapper;

    public SpotMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CreateSpotDto mapTo(SpotEntity spot) {
        return modelMapper.map(spot, CreateSpotDto.class);
    }

    @Override
    public SpotEntity mapFrom(CreateSpotDto spotDto) {
        return modelMapper.map(spotDto, SpotEntity.class);
    }

}
