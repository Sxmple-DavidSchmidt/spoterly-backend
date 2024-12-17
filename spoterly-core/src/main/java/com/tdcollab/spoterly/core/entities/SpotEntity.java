package com.tdcollab.spoterly.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "likedByUsers")
@Table(name = "spots")
public class SpotEntity {
    @NotNull(message = "id cannot be null")
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull(message = "author cannot be null")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "description cannot be null")
    private String description;

    @NotNull(message = "latitude cannot be null")
    private double latitude;

    @NotNull(message = "longitude cannot be null")
    private double longitude;

    @ManyToMany
    @JoinTable(
            name = "user_likes_spot",
            joinColumns = @JoinColumn(name = "spot_id"),
            inverseJoinColumns = @JoinColumn(name = "username")
    )
    private Set<UserEntity> likedByUsers = new HashSet<>();
}
