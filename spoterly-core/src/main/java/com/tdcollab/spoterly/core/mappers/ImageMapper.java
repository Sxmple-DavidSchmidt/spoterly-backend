package com.tdcollab.spoterly.core.mappers;

import com.tdcollab.spoterly.core.dtos.image.CreateImageDto;
import com.tdcollab.spoterly.core.dtos.image.ImageDto;
import com.tdcollab.spoterly.core.entities.ImageEntity;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {
    public ImageDto fromImageEntity(ImageEntity imageEntity) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(imageEntity.getId());
        imageDto.setImageData(imageEntity.getImageData());
        imageDto.setFileType(imageEntity.getFileType());
        return imageDto;
    }

    public ImageEntity entityFromImageDto(ImageDto imageDto) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setId(imageDto.getId());
        imageEntity.setImageData(imageDto.getImageData());
        imageEntity.setFileType(imageEntity.getFileType());
        return imageEntity;
    }

    public ImageEntity entityFromCreateImageDto(CreateImageDto createImageDto) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImageData(createImageDto.getImageData());
        imageEntity.setFileType(createImageDto.getFileType());
        return imageEntity;
    }
}
