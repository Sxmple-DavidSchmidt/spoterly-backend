package com.tdcollab.spoterly.core.dtos.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostDto {
    @JsonProperty("spot_id")
    private UUID spot;

    @JsonProperty("image_id")
    private UUID image;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;
}


