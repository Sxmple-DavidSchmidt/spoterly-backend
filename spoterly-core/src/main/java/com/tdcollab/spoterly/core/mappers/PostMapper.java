package com.tdcollab.spoterly.core.mappers;

import com.tdcollab.spoterly.core.dtos.post.CreatePostDto;
import com.tdcollab.spoterly.core.dtos.post.PostDto;
import com.tdcollab.spoterly.core.dtos.spot.SpotDto;
import com.tdcollab.spoterly.core.dtos.user.UserDto;
import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.services.SpotService;
import com.tdcollab.spoterly.core.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    private final UserService userService;
    private final SpotService spotService;
    private final SpotMapper spotMapper;
    private final UserMapper userMapper;

    public PostMapper(UserService userService, SpotService spotService, SpotMapper spotMapper, UserMapper userMapper) {
        this.userService = userService;
        this.spotService = spotService;
        this.spotMapper = spotMapper;
        this.userMapper = userMapper;
    }

    public PostDto fromPostEntity(PostEntity postEntity) {
        SpotDto spotDto = spotMapper.fromSpotEntity(spotService.findById(postEntity.getSpot().getId()));
        UserDto userDto = userMapper.fromUserEntity(userService.findByUsername(postEntity.getAuthor().getUsername()));

        PostDto postDto = new PostDto();
        postDto.setId(postEntity.getId());
        postDto.setAuthor(userDto);
        postDto.setSpot(spotDto);
        postDto.setContent(postEntity.getContent());
        return postDto;
    }

    public PostEntity entityFromPostDto(PostDto postDto) {
        PostEntity postEntity = new PostEntity();
        postEntity.setId(postDto.getId());
        postEntity.setAuthor(userService.findByUsername(postDto.getAuthor().getUsername()));
        postEntity.setSpot(spotService.findById(postDto.getSpot().getId()));
        postEntity.setContent(postDto.getContent());
        return postEntity;
    }

    public PostEntity entityFromCreatePostDto(CreatePostDto createPostDto) {
        UserEntity userEntity = userService.findByUsername(createPostDto.getAuthor());
        SpotEntity spotEntity = spotService.findById(createPostDto.getSpot());

        PostEntity postEntity = new PostEntity();
        postEntity.setAuthor(userEntity);
        postEntity.setSpot(spotEntity);
        postEntity.setContent(createPostDto.getContent());
        return postEntity;
    }
}
