package com.tdcollab.spoterly.services;

import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.repositories.PostRepository;
import com.tdcollab.spoterly.core.services.PostService;
import com.tdcollab.spoterly.core.exceptions.SpotNotFoundException;
import com.tdcollab.spoterly.repositories.SpotRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SpotRepository spotRepository;

    public PostServiceImpl(PostRepository postRepository, SpotRepository spotRepository) {
        this.postRepository = postRepository;
        this.spotRepository = spotRepository;
    }

    @Override
    public PostEntity createPost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    @Override
    public PostEntity findById(UUID id) {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if (postEntity.isEmpty()) throw new PostNotFoundException("Could not find Post with id \"" + id + "\"");
        return postEntity.get();
    }

    @Override
    public List<PostEntity> findAll() {
        return StreamSupport.stream(
                postRepository.findAll().spliterator(),
                false
        ).collect(
                Collectors.toList()
        );
    }

    @Override
    @Transactional
    public List<PostEntity> findBySpotId(UUID id) {
        Optional<SpotEntity> spotEntity = spotRepository.findById(id);
        if (spotEntity.isEmpty()) throw new SpotNotFoundException("Could not find Spot id \"" + id + "\"");
        return new ArrayList<>(postRepository.findAllBySpotId(id));
    }

    @Override
    public void deletePost(UUID id) {
        PostEntity postEntity = findById(id);
        postRepository.delete(postEntity);
    }
}
