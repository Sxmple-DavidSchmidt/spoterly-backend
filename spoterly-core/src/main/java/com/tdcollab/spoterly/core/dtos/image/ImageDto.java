package com.tdcollab.spoterly.core.dtos.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("image_data")
    private byte[] imageData;

    @JsonProperty("file_type")
    private String fileType;
}
