package com.tdcollab.spoterly.core.dtos.spot;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteSpotDto {
    @JsonProperty("id")
    private UUID id;
}
