package com.tdcollab.spoterly.core.dtos.spot;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tdcollab.spoterly.core.dtos.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotDto {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("author")
    private UserDto author;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("latitude")
    private double latitude;

    @JsonProperty("longitude")
    private double longitude;
}
