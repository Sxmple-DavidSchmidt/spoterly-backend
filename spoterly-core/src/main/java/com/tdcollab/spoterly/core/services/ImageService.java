package com.tdcollab.spoterly.core.services;

import com.tdcollab.spoterly.core.entities.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ImageService {
    ImageEntity uploadImage(MultipartFile file) throws IOException;
    ImageEntity findById(UUID id);
    void deleteImage(UUID id);
}
