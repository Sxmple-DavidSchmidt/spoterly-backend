package com.tdcollab.spoterly.core.mappers.impl;

import com.tdcollab.spoterly.core.dtos.SpotDto;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class SpotMapper implements Mapper<SpotEntity, SpotDto> {
    private final ModelMapper modelMapper;

    public SpotMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public SpotDto mapTo(SpotEntity spot) {
        return modelMapper.map(spot, SpotDto.class);
    }

    @Override
    public SpotEntity mapFrom(SpotDto spotDto) {
        return modelMapper.map(spotDto, SpotEntity.class);
    }
}
