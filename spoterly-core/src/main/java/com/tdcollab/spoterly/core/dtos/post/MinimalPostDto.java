package com.tdcollab.spoterly.core.dtos.post;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinimalPostDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("spot_id")
    private UUID spotId;

    @JsonProperty("author_id")
    private String authorId;

    @JsonProperty("image_id")
    private UUID imageId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;
}
