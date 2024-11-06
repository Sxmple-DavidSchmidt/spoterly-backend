package com.tdcollab.spoterly.core.services;

import com.tdcollab.spoterly.core.entities.UserEntity;

import java.util.UUID;

public interface UserService {
    UserEntity createUser(UserEntity user);
    UserEntity findByUsername(String username);
    void likePost(String username, UUID postId);
    void unlikePost(String username, UUID postId);
    void likeSpot(String username, UUID spotId);
    void unlikeSpot(String username, UUID spotId);
}
