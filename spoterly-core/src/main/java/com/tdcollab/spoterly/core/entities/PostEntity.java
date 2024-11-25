package com.tdcollab.spoterly.core.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(exclude = "likedByUsers")
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "spot_id")
    private SpotEntity spot;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    private String title;

    private String content;

    @ManyToMany
    @JoinTable(
            name = "user_likes_post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "username")
    )
    private Set<UserEntity> likedByUsers = new HashSet<>();
}
