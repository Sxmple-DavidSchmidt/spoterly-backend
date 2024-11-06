package com.tdcollab.spoterly.core.mappers.impl;

import com.tdcollab.spoterly.core.dtos.ImageDto;
import com.tdcollab.spoterly.core.entities.ImageEntity;
import com.tdcollab.spoterly.core.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper implements Mapper<ImageEntity, ImageDto> {
    private final ModelMapper modelMapper;

    public ImageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ImageDto mapTo(ImageEntity imageEntity) {
        return modelMapper.map(imageEntity, ImageDto.class);
    }

    @Override
    public ImageEntity mapFrom(ImageDto imageDto) {
        return modelMapper.map(imageDto, ImageEntity.class);
    }
}
