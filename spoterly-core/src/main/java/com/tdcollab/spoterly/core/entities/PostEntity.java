package com.tdcollab.spoterly.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "id cannot be null")
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @NotNull(message = "spot cannot be null")
    @ManyToOne
    @JoinColumn(name = "spot_id")
    private SpotEntity spot;

    @NotNull(message = "author cannot be null")
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @NotNull(message = "image cannot be null")
    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageEntity image;

    @NotNull(message = "title cannot be null")
    private String title;

    @NotNull(message = "content cannot be null")
    private String content;

    @ManyToMany
    @JoinTable(
            name = "user_likes_post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "username")
    )
    private Set<UserEntity> likedByUsers = new HashSet<>();
}
