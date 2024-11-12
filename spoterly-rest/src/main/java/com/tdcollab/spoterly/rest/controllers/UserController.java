package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.PostDto;
import com.tdcollab.spoterly.core.dtos.SpotDto;
import com.tdcollab.spoterly.core.dtos.UserDto;
import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.exceptions.*;
import com.tdcollab.spoterly.core.mappers.Mapper;
import com.tdcollab.spoterly.core.mappers.impl.PostMapper;
import com.tdcollab.spoterly.core.mappers.impl.SpotMapper;
import com.tdcollab.spoterly.core.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;
    private final PostMapper postMapper;
    private final SpotMapper spotMapper;

    public UserController(UserService userService, Mapper<UserEntity, UserDto> userMapper, PostMapper postMapper, SpotMapper spotMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
        this.spotMapper = spotMapper;
    }

    @PostMapping(path = "")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        UserEntity mappedUser = userMapper.mapFrom(user);
        UserEntity savedUser = userService.createUser(mappedUser);
        return new ResponseEntity<>(userMapper.mapTo(savedUser),HttpStatus.CREATED);
    }

    @PostMapping(path = "/{username}/likePost/{postId}")
    public ResponseEntity<String> likePost(@PathVariable("username") String username, @PathVariable("postId") UUID postId) {
        userService.likePost(username, postId);
        return ResponseEntity.ok("Post liked successfully");
    }

    @PostMapping(path = "/{username}/unlikePost/{postId}")
    public ResponseEntity<String> unlikePost(@PathVariable("username") String username, @PathVariable("postId") UUID postId) {
        userService.unlikePost(username, postId);
        return ResponseEntity.ok("Post liked successfully");
    }

    @PostMapping(path = "/{username}/likeSpot/{spotId}")
    public ResponseEntity<String> likeSpot(@PathVariable("username") String username, @PathVariable("spotId") UUID spotId) {
        userService.likeSpot(username, spotId);
        return ResponseEntity.ok("Spot liked successfully");
    }

    @PostMapping(path = "/{username}/unlikeSpot/{spotId}")
    public ResponseEntity<String> unlikeSpot(@PathVariable("username") String username, @PathVariable("spotId") UUID spotId) {
        userService.unlikeSpot(username, spotId);
        return ResponseEntity.ok("Spot liked successfully");
    }

    @GetMapping(path = "/{username}")
    public UserDto getUserByUsername(@PathVariable("username") String username) {
        return userMapper.mapTo(userService.findByUsername(username));
    }

    @GetMapping(path = "/{username}/likedPosts")
    public List<PostDto> getLikedPostsByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username).getLikedPosts()
                .stream()
                .map(postMapper::mapTo)
                .toList();
    }

//    @GetMapping(path = "/{username}/likedSpots")
//    public List<SpotDto> getLikedSpotsByUsername(@PathVariable("username") String username) {
//        return userService.findByUsername(username).getLikedSpots()
//                .stream()
//                .map(spotMapper::mapTo)
//                .toList();
//    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SpotNotFoundException.class)
    public ResponseEntity<String> handleSpotNotFoundException(SpotNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SpotAlreadyLikedException.class)
    public ResponseEntity<String> handleSpotAlreadyLikedException(SpotAlreadyLikedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(SpotNotLikedException.class)
    public ResponseEntity<String> handleSpotNotLikedException(SpotNotLikedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> handlePostNotFoundException(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PostAlreadyLikedException.class)
    public ResponseEntity<String> handlePostAlreadyLikedException(PostAlreadyLikedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(PostNotLikedException.class)
    public ResponseEntity<String> handlePostNotLikedException(PostNotLikedException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }
}
