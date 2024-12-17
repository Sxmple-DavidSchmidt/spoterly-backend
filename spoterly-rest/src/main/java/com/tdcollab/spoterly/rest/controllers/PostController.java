package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.post.CreatePostDto;
import com.tdcollab.spoterly.core.dtos.post.MinimalPostDto;
import com.tdcollab.spoterly.core.dtos.user.MinimalUserDto;
import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.exceptions.PostNotFoundException;
import com.tdcollab.spoterly.core.exceptions.SpotNotFoundException;
import com.tdcollab.spoterly.core.exceptions.UserNotFoundException;
import com.tdcollab.spoterly.core.mappers.PostMapper;
import com.tdcollab.spoterly.core.mappers.UserMapper;
import com.tdcollab.spoterly.core.services.PostService;
import com.tdcollab.spoterly.core.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("api/posts")
public class PostController {
    private final PostMapper postMapper;
    private final PostService postService;
    private final UserMapper userMapper;
    private final UserService userService;

    public PostController(PostMapper postMapper, PostService postService, UserMapper userMapper, UserService userService) {
        this.postMapper = postMapper;
        this.postService = postService;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping("/{username}/createPost")
    @PreAuthorize("@userSecurity.isCurrentUser(#username)")
    public ResponseEntity<MinimalPostDto> createPost(@RequestBody CreatePostDto createPostDto, @PathVariable String username) {
        UserEntity userEntity = userService.findByUsername(username);

        PostEntity postEntity = postMapper.entityFromCreatePostDto(createPostDto);
        postEntity.setAuthor(userEntity);
        PostEntity savedPost = postService.createPost(postEntity);
        MinimalPostDto minimalSavedPostDto = postMapper.minimalFromPostEntity(savedPost);
        return new ResponseEntity<>(minimalSavedPostDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<MinimalPostDto> getPosts() {
        List<PostEntity> postEntities = postService.findAll();
        return postEntities
                .stream()
                .map(postMapper::minimalFromPostEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public MinimalPostDto getPostById(@PathVariable("id") UUID id) {
        PostEntity postEntity = postService.findById(id);
        return postMapper.minimalFromPostEntity(postEntity);
    }

    @GetMapping("/{id}/likingUsers")
    public Set<MinimalUserDto> getLikingUsers(@PathVariable("id") UUID id) {
        PostEntity postEntity = postService.findById(id);
        return postEntity.getLikedByUsers()
                .stream()
                .map(userMapper::minimalFromUserEntity)
                .collect(Collectors.toSet());
    }

    @GetMapping("/spot/{id}")
    public List<MinimalPostDto> getPostsBySpot(@PathVariable("id") UUID id) {
        List<PostEntity> postEntities = postService.findBySpotId(id);
        return postEntities
                .stream()
                .map(postMapper::minimalFromPostEntity)
                .toList();
    }

    @GetMapping("/spot/getPostsByUser/{username}")
    public List<MinimalPostDto> getPostsByUser(@PathVariable("username") String username) {
        List<PostEntity> postEntities = postService.findByUsername(username);
        return postEntities
                .stream()
                .map(postMapper::minimalFromPostEntity)
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

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> handlePostNotFoundException(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
