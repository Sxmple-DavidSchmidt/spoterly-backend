package com.tdcollab.spoterly.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EqualsAndHashCode(exclude = {"likedPosts", "likedSpots"})
@Table(name = "users")
public class UserEntity implements UserDetails {
    @NotNull(message = "id cannot be null")
    @Id
    private String username;

    @NotNull(message = "firstname cannot be null")
    private String firstname;

    @NotNull(message = "lastname cannot be null")
    private String lastname;

    @NotNull(message = "password cannot be null")
    private String password;

    @NotNull(message = "role cannot be null")
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "image_id")
    private ImageEntity profilePicture;

    @ManyToMany(mappedBy = "likedByUsers", fetch = FetchType.EAGER)
    private Set<PostEntity> likedPosts = new HashSet<>();

    @ManyToMany(mappedBy = "likedByUsers", fetch = FetchType.EAGER)
    private Set<SpotEntity> likedSpots = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}
