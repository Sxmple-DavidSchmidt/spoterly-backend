package com.tdcollab.spoterly.core.mappers;

import com.tdcollab.spoterly.core.dtos.post.CreatePostDto;
import com.tdcollab.spoterly.core.dtos.post.MinimalPostDto;
import com.tdcollab.spoterly.core.dtos.post.PostDto;
import com.tdcollab.spoterly.core.dtos.spot.SpotDto;
import com.tdcollab.spoterly.core.dtos.user.UserDto;
import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.services.SpotService;
import com.tdcollab.spoterly.core.services.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

    public MinimalPostDto minimalFromPostEntity(PostEntity postEntity) {
        MinimalPostDto minimalPostDto = new MinimalPostDto();
        String author_id = postEntity.getAuthor().getUsername();
        UUID spot_id = postEntity.getSpot().getId();

        minimalPostDto.setId(postEntity.getId());
        minimalPostDto.setContent(postEntity.getContent());
        minimalPostDto.setAuthor_id(author_id);
        minimalPostDto.setSpot_id(spot_id);
        return minimalPostDto;
    }

    public PostEntity entityFromCreatePostDto(CreatePostDto createPostDto) {
        SpotEntity spotEntity = spotService.findById(createPostDto.getSpot());

        PostEntity postEntity = new PostEntity();
        postEntity.setSpot(spotEntity);
        postEntity.setContent(createPostDto.getContent());
        return postEntity;
    }
}
