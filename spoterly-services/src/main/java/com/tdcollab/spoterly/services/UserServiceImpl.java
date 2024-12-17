package com.tdcollab.spoterly.services;

import com.tdcollab.spoterly.core.entities.*;
import com.tdcollab.spoterly.core.services.ImageService;
import com.tdcollab.spoterly.core.services.PostService;
import com.tdcollab.spoterly.core.services.SpotService;
import com.tdcollab.spoterly.repositories.UserRepository;
import com.tdcollab.spoterly.core.services.UserService;
import com.tdcollab.spoterly.core.exceptions.PostAlreadyLikedException;
import com.tdcollab.spoterly.core.exceptions.SpotAlreadyLikedException;
import com.tdcollab.spoterly.core.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PostService postService;
    private final SpotService spotService;
    private final ImageService imageService;

    public UserServiceImpl(UserRepository userRepository, PostService postService, SpotService spotService, ImageService imageService) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.spotService = spotService;
        this.imageService = imageService;
    }

    @Override
    @Transactional()
    public UserEntity findByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty())
            throw new UserNotFoundException("Could not find user with username: \"" + username + "\"");
        return userEntity.get();
    }

    @Override
    @Transactional
    public void likePost(String username, UUID postId) {
        UserEntity userEntity = findByUsername(username);
        PostEntity postEntity = postService.findById(postId);

        if (!userEntity.getLikedPosts().contains(postEntity) && !postEntity.getLikedByUsers().contains(userEntity)) {
            userEntity.getLikedPosts().add(postEntity);
            postEntity.getLikedByUsers().add(userEntity);
        } else {
            throw new PostAlreadyLikedException("User \"" + username + "\" already liked post with id \"" + postId + "\"");
        }

        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void unlikePost(String username, UUID postId) {
        UserEntity userEntity = findByUsername(username);
        PostEntity postEntity = postService.findById(postId);

        if (userEntity.getLikedPosts().contains(postEntity) && postEntity.getLikedByUsers().contains(userEntity)) {
            userEntity.getLikedPosts().remove(postEntity);
            postEntity.getLikedByUsers().remove(userEntity);
        } else {
            throw new PostAlreadyLikedException("User \"" + username + "\" has not liked post with id \"" + postId + "\"");
        }
    }

    @Override
    @Transactional
    public void likeSpot(String username, UUID spotId) {
        UserEntity userEntity = findByUsername(username);
        SpotEntity spotEntity = spotService.findById(spotId);

        if (!userEntity.getLikedSpots().contains(spotEntity) && !spotEntity.getLikedByUsers().contains(userEntity)) {
            userEntity.getLikedSpots().add(spotEntity);
            spotEntity.getLikedByUsers().add(userEntity);
        } else {
            throw new SpotAlreadyLikedException("User \"" + username + "\" already liked spot with id \"" + spotId + "\"");
        }

        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void unlikeSpot(String username, UUID spotId) {
        UserEntity userEntity = findByUsername(username);
        SpotEntity spotEntity = spotService.findById(spotId);

        if (userEntity.getLikedSpots().contains(spotEntity) && spotEntity.getLikedByUsers().contains(userEntity)) {
            userEntity.getLikedSpots().remove(spotEntity);
            spotEntity.getLikedByUsers().remove(userEntity);
        } else {
            throw new PostAlreadyLikedException("User \"" + username + "\" has not liked spot with id \"" + spotId + "\"");
        }

        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        UserEntity userEntity = findByUsername(username);
        userRepository.delete(userEntity);
    }

    @Override
    @Transactional
    public void setRole(String username, Role role) {
        UserEntity userEntity = findByUsername(username);
        userEntity.setRole(role);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void setProfilePicture(String username, UUID imageId) {
        UserEntity userEntity = findByUsername(username);
        ImageEntity imageEntity = imageService.findById(imageId);
        userEntity.setProfilePicture(imageEntity);
        userRepository.save(userEntity);
    }
}
