package com.tdcollab.spoterly.core.dtos;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private UUID id;

    private SpotDto spot;

    private UserDto author;

    private String content;
}
