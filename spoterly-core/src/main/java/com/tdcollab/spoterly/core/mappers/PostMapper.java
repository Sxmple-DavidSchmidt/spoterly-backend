package com.tdcollab.spoterly.core.mappers;

import com.tdcollab.spoterly.core.dtos.post.CreatePostDto;
import com.tdcollab.spoterly.core.dtos.post.MinimalPostDto;
import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.services.SpotService;
import com.tdcollab.spoterly.core.services.UserService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PostMapper {
    private final SpotService spotService;

    public PostMapper(SpotService spotService) {
        this.spotService = spotService;
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
