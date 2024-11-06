package com.tdcollab.spoterly.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private UUID id;

    private byte[] imageData;

    private String fileType;
}
