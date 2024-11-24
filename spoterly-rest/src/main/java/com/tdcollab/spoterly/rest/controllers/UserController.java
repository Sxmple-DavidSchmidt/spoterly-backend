package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.post.MinimalPostDto;
import com.tdcollab.spoterly.core.dtos.spot.MinimalSpotDto;
import com.tdcollab.spoterly.core.dtos.user.MinimalUserDto;
import com.tdcollab.spoterly.core.exceptions.*;
import com.tdcollab.spoterly.core.mappers.PostMapper;
import com.tdcollab.spoterly.core.mappers.SpotMapper;
import com.tdcollab.spoterly.core.mappers.UserMapper;
import com.tdcollab.spoterly.core.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping(path = "/{username}/likePost/{postId}")
    @PreAuthorize("@userSecurity.isCurrentUser(#username)")
    public ResponseEntity<String> likePost(@PathVariable("username") String username, @PathVariable("postId") String postIdString) {
        UUID postId = UUID.fromString(postIdString);
        userService.likePost(username, postId);
        return ResponseEntity.ok("Post liked successfully");
    }

    @PostMapping(path = "/{username}/unlikePost/{postId}")
    @PreAuthorize("@userSecurity.isCurrentUser(#username)")
    public ResponseEntity<String> unlikePost(@PathVariable("username") String username, @PathVariable("postId") String postIdString) {
        UUID postId = UUID.fromString(postIdString);
        userService.unlikePost(username, postId);
        return ResponseEntity.ok("Post unliked successfully");
    }

    @PostMapping(path = "/{username}/likeSpot/{spotId}")
    @PreAuthorize("@userSecurity.isCurrentUser(#username)")
    public ResponseEntity<String> likeSpot(@PathVariable("username") String username, @PathVariable("spotId") String spotIdString) {
        UUID spotId = UUID.fromString(spotIdString);
        userService.likeSpot(username, spotId);
        return ResponseEntity.ok("Spot liked successfully");
    }

    @PostMapping(path = "/{username}/unlikeSpot/{spotId}")
    @PreAuthorize("@userSecurity.isCurrentUser(#username)")
    public ResponseEntity<String> unlikeSpot(@PathVariable("username") String username, @PathVariable("spotId") String spotIdString) {
        UUID spotId = UUID.fromString(spotIdString);
        userService.unlikeSpot(username, spotId);
        return ResponseEntity.ok("Spot unliked successfully");
    }

    @GetMapping(path = "/{username}")
    public MinimalUserDto getUserByUsername(@PathVariable("username") String username) {
        return userMapper.minimalFromUserEntity(userService.findByUsername(username));
    }

    @GetMapping(path = "/{username}/likedPosts")
    public List<MinimalPostDto> getLikedPostsByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username).getLikedPosts()
                .stream()
                .map(postMapper::minimalFromPostEntity)
                .toList();
    }

    @GetMapping(path = "/{username}/likedSpots")
    public List<MinimalSpotDto> getLikedSpotsByUsername(@PathVariable("username") String username) {
        return userService.findByUsername(username).getLikedSpots()
                .stream()
                .map(spotMapper::minimalFromSpotEntity)
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
