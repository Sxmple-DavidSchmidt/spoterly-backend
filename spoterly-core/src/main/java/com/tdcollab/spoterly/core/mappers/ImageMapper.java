package com.tdcollab.spoterly.core.mappers;

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
}
