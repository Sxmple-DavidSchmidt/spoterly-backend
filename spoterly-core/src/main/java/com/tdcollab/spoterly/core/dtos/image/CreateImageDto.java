package com.tdcollab.spoterly.core.dtos.image;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateImageDto {
    @JsonProperty("image_data")
    private byte[] imageData;

    @JsonProperty("file_type")
    private String fileType;
}
