package com.tdcollab.spoterly.core.entities;

import jakarta.persistence.*;
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
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String name;

    private String description;

    private double latitude;

    private double longitude;

    @ManyToMany
    @JoinTable(
            name = "user_likes_spot",
            joinColumns = @JoinColumn(name = "spot_id"),
            inverseJoinColumns = @JoinColumn(name = "username")
    )
    private Set<UserEntity> likedByUsers = new HashSet<>();
}
