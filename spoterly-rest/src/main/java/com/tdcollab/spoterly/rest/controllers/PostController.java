package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.post.CreatePostDto;
import com.tdcollab.spoterly.core.dtos.post.PostDto;
import com.tdcollab.spoterly.core.dtos.user.UserDto;
import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.exceptions.PostNotFoundException;
import com.tdcollab.spoterly.core.exceptions.SpotNotFoundException;
import com.tdcollab.spoterly.core.exceptions.UserNotFoundException;
import com.tdcollab.spoterly.core.mappers.PostMapper;
import com.tdcollab.spoterly.core.mappers.UserMapper;
import com.tdcollab.spoterly.core.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostMapper postMapper;
    private final PostService postService;
    private final UserMapper userMapper;

    public PostController(PostMapper postMapper, PostService postService, UserMapper userMapper) {
        this.postMapper = postMapper;
        this.postService = postService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody CreatePostDto createPostDto) {
        PostEntity postEntity = postMapper.entityFromCreatePostDto(createPostDto);
        PostEntity savedPost = postService.createPost(postEntity);
        PostDto savedPostDto = postMapper.fromPostEntity(savedPost);
        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<PostDto> getPosts() {
        List<PostEntity> postEntities = postService.findAll();
        return postEntities
                .stream()
                .map(postMapper::fromPostEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") UUID id) {
        PostEntity postEntity = postService.findById(id);
        return postMapper.fromPostEntity(postEntity);
    }

    @GetMapping("/{id}/likingUsers")
    public Set<UserDto> getLikingUsers(@PathVariable("id") UUID id) {
        PostEntity postEntity = postService.findById(id);
        return postEntity.getLikedByUsers()
                .stream()
                .map(userMapper::fromUserEntity)
                .collect(Collectors.toSet());
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
