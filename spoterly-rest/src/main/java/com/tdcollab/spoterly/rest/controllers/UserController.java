package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.post.PostDto;
import com.tdcollab.spoterly.core.dtos.spot.SpotDto;
import com.tdcollab.spoterly.core.dtos.user.UserDto;
import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.exceptions.*;
import com.tdcollab.spoterly.core.mappers.PostMapper;
import com.tdcollab.spoterly.core.mappers.SpotMapper;
import com.tdcollab.spoterly.core.mappers.UserMapper;
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
    private final UserMapper userMapper;
    private final PostMapper postMapper;
    private final SpotMapper spotMapper;

    public UserController(UserService userService, UserMapper userMapper, PostMapper postMapper, SpotMapper spotMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.postMapper = postMapper;
        this.spotMapper = spotMapper;
    }

    @PostMapping()
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        UserEntity mappedUser = userMapper.fromUserDto(user);
        UserEntity savedUser = userService.createUser(mappedUser);
        return new ResponseEntity<>(userMapper.fromUserEntity(savedUser),HttpStatus.CREATED);
    }

    @PostMapping(path = "/{username}/likePost/{postId}")
    public ResponseEntity<String> likePost(@PathVariable("username") String username, @PathVariable("postId") String postIdString) {
        UUID postId = UUID.fromString(postIdString);
        userService.likePost(username, postId);
        return ResponseEntity.ok("Post liked successfully");
    }

    @PostMapping(path = "/{username}/unlikePost/{postId}")
    public ResponseEntity<String> unlikePost(@PathVariable("username") String username, @PathVariable("postId") String postIdString) {
        UUID postId = UUID.fromString(postIdString);
        userService.unlikePost(username, postId);
        return ResponseEntity.ok("Post unliked successfully");
    }

    @PostMapping(path = "/{username}/likeSpot/{spotId}")
    public ResponseEntity<String> likeSpot(@PathVariable("username") String username, @PathVariable("spotId") String spotIdString) {
        UUID spotId = UUID.fromString(spotIdString);
        userService.likeSpot(username, spotId);
        return ResponseEntity.ok("Spot liked successfully");
    }

    @PostMapping(path = "/{username}/unlikeSpot/{spotId}")
    public ResponseEntity<String> unlikeSpot(@PathVariable("username") String username, @PathVariable("spotId") String spotIdString) {
        UUID spotId = UUID.fromString(spotIdString);
        userService.unlikeSpot(username, spotId);
        return ResponseEntity.ok("Spot unliked successfully");
    }

    @GetMapping(path = "/{username}")
    public UserDto getUserByUsername(@PathVariable("username") String username) {
        return userMapper.fromUserEntity(userService.findByUsername(username));
    }

    @GetMapping(path = "/{username}/likedPosts")
    public List<PostDto> getLikedPostsByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username).getLikedPosts()
                .stream()
                .map(postMapper::fromPostEntity)
                .toList();
    }

    @GetMapping(path = "/{username}/likedSpots")
    public List<SpotDto> getLikedSpotsByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username).getLikedSpots()
                .stream()
                .map(spotMapper::fromSpotEntity)
                .toList();
    }

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
