package com.tdcollab.spoterly.core.dtos.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdcollab.spoterly.core.dtos.image.ImageDto;
import com.tdcollab.spoterly.core.dtos.spot.SpotDto;
import com.tdcollab.spoterly.core.dtos.user.UserDto;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("spot")
    private SpotDto spot;

    @JsonProperty("author")
    private UserDto author;

    @JsonProperty("image")
    private ImageDto image;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;
}
