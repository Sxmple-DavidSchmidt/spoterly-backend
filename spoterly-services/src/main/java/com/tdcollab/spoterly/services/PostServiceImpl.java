package com.tdcollab.spoterly.services;

import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.repositories.PostRepository;
import com.tdcollab.spoterly.core.services.PostService;
import com.tdcollab.spoterly.core.exceptions.SpotNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostEntity createPost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    @Override
    public PostEntity findById(UUID id) {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if (postEntity.isEmpty()) throw new SpotNotFoundException("Could not find Post with id \"" + id + "\"");
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
    public void deletePost(UUID id) {
        PostEntity postEntity = findById(id);
        postRepository.delete(postEntity);
    }
}
