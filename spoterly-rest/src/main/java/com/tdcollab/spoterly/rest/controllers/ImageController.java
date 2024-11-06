package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.ImageDto;
import com.tdcollab.spoterly.core.entities.ImageEntity;
import com.tdcollab.spoterly.core.exceptions.ImageNotFoundException;
import com.tdcollab.spoterly.core.mappers.Mapper;
import com.tdcollab.spoterly.core.services.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class ImageController {
    private final Mapper<ImageEntity, ImageDto> imageMapper;
    private final ImageService imageService;

    public ImageController(Mapper<ImageEntity, ImageDto> imageMapper, ImageService imageService) {
        this.imageMapper = imageMapper;
        this.imageService = imageService;
    }

    @PostMapping(path = "/images")
    public ResponseEntity<ImageDto> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            ImageEntity savedImage = imageService.uploadImage(file);
            ImageDto mappedImage = imageMapper.mapTo(savedImage);
            log.warn(mappedImage.getId().toString());
            return new ResponseEntity<>(mappedImage, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/images/{id}")
    public ResponseEntity<ByteArrayResource> getImageById(@PathVariable("id") UUID id) {
        ImageEntity imageEntity = imageService.findById(id);
        ImageDto imageDto = imageMapper.mapTo(imageEntity);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageDto.getFileType()))
                .body(new ByteArrayResource(imageDto.getImageData()));
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<String> handleImageNotFoundException(ImageNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
