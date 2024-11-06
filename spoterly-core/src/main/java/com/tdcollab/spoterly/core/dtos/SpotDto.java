package com.tdcollab.spoterly.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SpotDto {
    private UUID id;

    private String name;

    private String description;

    private double latitude;

    private double longitude;

    private Set<UserDto> likedBy;
}
