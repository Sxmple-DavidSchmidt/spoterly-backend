package com.tdcollab.spoterly.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;

    private String firstname;

    private String lastname;

    private Set<PostDto> likedPosts;

    private Set<SpotDto> likedSpots;
}
