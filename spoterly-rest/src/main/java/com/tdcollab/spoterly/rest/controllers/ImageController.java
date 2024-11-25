package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.image.ImageDto;
import com.tdcollab.spoterly.core.entities.ImageEntity;
import com.tdcollab.spoterly.core.exceptions.ImageNotFoundException;
import com.tdcollab.spoterly.core.mappers.ImageMapper;
import com.tdcollab.spoterly.core.services.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("api/images")
public class ImageController {
    private final ImageService imageService;
    private final ImageMapper imageMapper;

    public ImageController(ImageMapper imageMapper, ImageService imageService) {
        this.imageMapper = imageMapper;
        this.imageService = imageService;
    }

    @PostMapping
    public ResponseEntity<ImageDto> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            ImageEntity savedImage = imageService.uploadImage(file);
            ImageDto mappedImage = imageMapper.fromImageEntity(savedImage);
            return new ResponseEntity<>(mappedImage, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ByteArrayResource> getImageById(@PathVariable("id") UUID id) {
        ImageEntity imageEntity = imageService.findById(id);
        ImageDto imageDto = imageMapper.fromImageEntity(imageEntity);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageDto.getFileType()))
                .body(new ByteArrayResource(imageDto.getImageData()));
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<String> handleImageNotFoundException(ImageNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
