package com.tdcollab.spoterly.services;

import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.services.PostService;
import com.tdcollab.spoterly.core.services.SpotService;
import com.tdcollab.spoterly.repositories.UserRepository;
import com.tdcollab.spoterly.core.services.UserService;
import com.tdcollab.spoterly.core.exceptions.PostAlreadyLikedException;
import com.tdcollab.spoterly.core.exceptions.SpotAlreadyLikedException;
import com.tdcollab.spoterly.core.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PostService postService;
    private final SpotService spotService;

    public UserServiceImpl(UserRepository userRepository, PostService postService, SpotService spotService) {
        this.userRepository = userRepository;
        this.postService = postService;
        this.spotService = spotService;
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity findByUsername(String username) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        if (userEntity.isEmpty()) throw new UserNotFoundException("Could not find User with username: \"" + username + "\"");
        return userEntity.get();
    }

    @Override
    public List<UserEntity> findAll() {
        return StreamSupport.stream(
                userRepository.findAll().spliterator(),
                false
        ).collect(
                Collectors.toList()
        );
    }

    @Override
    public List<UserEntity> findByAttribute(String username, String surname, String name) {
        return userRepository.findByAttribute(username, surname, name);
    }

    @Override
    public Set<SpotEntity> findLikedSpots(String username) {
        return findByUsername(username).getLikedSpots();
    }

    @Override
    public Set<PostEntity> findLikedPosts(String username) {
        return findByUsername(username).getLikedPosts();
    }

    @Override
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
}
