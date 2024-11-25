package com.tdcollab.spoterly.core.mappers;

import com.tdcollab.spoterly.core.dtos.post.CreatePostDto;
import com.tdcollab.spoterly.core.dtos.post.MinimalPostDto;
import com.tdcollab.spoterly.core.entities.ImageEntity;
import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.services.ImageService;
import com.tdcollab.spoterly.core.services.SpotService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PostMapper {
    private final SpotService spotService;
    private final ImageService imageService;

    public PostMapper(SpotService spotService, ImageService imageService) {
        this.spotService = spotService;
        this.imageService = imageService;
    }

    public MinimalPostDto minimalFromPostEntity(PostEntity postEntity) {
        MinimalPostDto minimalPostDto = new MinimalPostDto();
        String author_id = postEntity.getAuthor().getUsername();
        UUID spot_id = postEntity.getSpot().getId();

        minimalPostDto.setId(postEntity.getId());
        minimalPostDto.setAuthor_id(author_id);
        minimalPostDto.setSpot_id(spot_id);
        minimalPostDto.setImage(postEntity.getImage().getId());
        minimalPostDto.setTitle(postEntity.getTitle());
        minimalPostDto.setContent(postEntity.getContent());
        return minimalPostDto;
    }

    public PostEntity entityFromCreatePostDto(CreatePostDto createPostDto) {
        SpotEntity spotEntity = spotService.findById(createPostDto.getSpot());
        ImageEntity imageEntity = imageService.findById(createPostDto.getImage());

        PostEntity postEntity = new PostEntity();
        postEntity.setSpot(spotEntity);
        postEntity.setContent(createPostDto.getContent());
        postEntity.setTitle(createPostDto.getTitle());
        postEntity.setImage(imageEntity);
        return postEntity;
    }
}
