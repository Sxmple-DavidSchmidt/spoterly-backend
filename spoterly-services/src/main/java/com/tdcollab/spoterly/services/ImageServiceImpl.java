package com.tdcollab.spoterly.services;

import com.tdcollab.spoterly.core.entities.ImageEntity;
import com.tdcollab.spoterly.core.services.ImageService;
import com.tdcollab.spoterly.repositories.ImageRepository;
import com.tdcollab.spoterly.core.exceptions.ImageNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public ImageEntity uploadImage(MultipartFile file) throws IOException {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImageData(file.getBytes());
        imageEntity.setFileType(file.getContentType());
        return imageRepository.save(imageEntity);
    }

    @Override
    public ImageEntity findById(UUID id) {
        Optional<ImageEntity> imageEntity = imageRepository.findById(id);
        if (imageEntity.isEmpty()) throw new ImageNotFoundException("Could not find image with id: \"" + id + "\"");
        return imageEntity.get();

    }

    @Override
    public void deleteImage(UUID id) {
        ImageEntity imageEntity = findById(id);
        imageRepository.delete(imageEntity);
    }
}
