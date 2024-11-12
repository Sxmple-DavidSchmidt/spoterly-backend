package com.tdcollab.spoterly.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSpotDto {

    private String name;

    private String description;

    private double latitude;

    private double longitude;

    private String city;
}