package com.tdcollab.spoterly.core.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"likedPosts", "likedSpots"})
@Table(name = "users")
public class UserEntity {
    @Id
    private String username;

    private String firstname;

    private String lastname;

    @ManyToMany(mappedBy = "likedByUsers")
    private Set<PostEntity> likedPosts = new HashSet<>();

    @ManyToMany(mappedBy = "likedByUsers")
    private Set<SpotEntity> likedSpots = new HashSet<>();
}
