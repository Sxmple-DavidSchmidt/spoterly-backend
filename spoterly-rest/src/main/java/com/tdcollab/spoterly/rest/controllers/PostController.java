package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.PostDto;
import com.tdcollab.spoterly.core.dtos.SpotDto;
import com.tdcollab.spoterly.core.dtos.UserDto;
import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.exceptions.PostNotFoundException;
import com.tdcollab.spoterly.core.exceptions.SpotNotFoundException;
import com.tdcollab.spoterly.core.exceptions.UserNotFoundException;
import com.tdcollab.spoterly.core.mappers.Mapper;
import com.tdcollab.spoterly.core.mappers.impl.UserMapper;
import com.tdcollab.spoterly.core.services.PostService;
import com.tdcollab.spoterly.core.services.SpotService;
import com.tdcollab.spoterly.core.services.UserService;
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
    private final Mapper<PostEntity, PostDto> postMapper;
    private final PostService postService;
    private final SpotService spotService;
    private final UserService userService;
    private final UserMapper userMapper;

    public PostController(Mapper<PostEntity, PostDto> postMapper, PostService postService, SpotService spotService, UserService userService, UserMapper userMapper) {
        this.postMapper = postMapper;
        this.postService = postService;
        this.spotService = spotService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        SpotDto spotDto = postDto.getSpot();
        if (spotDto == null) throw new SpotNotFoundException("Spot is null");

        UserDto userDto = postDto.getAuthor();
        if (userDto == null) throw new UserNotFoundException("User is null");

        SpotEntity spotEntity = spotService.findById(spotDto.getId());
        UserEntity userEntity = userService.findByUsername(userDto.getUsername());

        PostEntity postEntity = postMapper.mapFrom(postDto);
        postEntity.setSpot(spotEntity);
        postEntity.setAuthor(userEntity);

        PostEntity savedPost = postService.createPost(postEntity);
        PostDto savedPostDto = postMapper.mapTo(savedPost);
        return new ResponseEntity<>(savedPostDto, HttpStatus.CREATED);
    }

    @GetMapping("")
    public List<PostDto> getPosts() {
        List<PostEntity> postEntities = postService.findAll();
        return postEntities
                .stream()
                .map(postMapper::mapTo)
                .toList();
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable("id") UUID id) {
        PostEntity postEntity = postService.findById(id);
        return postMapper.mapTo(postEntity);
    }

    @GetMapping("/{id}/likingUsers")
    public Set<UserDto> getLikingUsers(@PathVariable("id") UUID id) {
        PostEntity postEntity = postService.findById(id);
        return postEntity.getLikedByUsers()
                .stream()
                .map(userMapper::mapTo)
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
