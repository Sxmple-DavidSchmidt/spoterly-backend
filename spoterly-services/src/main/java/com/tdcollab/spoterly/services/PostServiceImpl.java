package com.tdcollab.spoterly.services;

import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.exceptions.PostNotFoundException;
import com.tdcollab.spoterly.core.exceptions.SpotNotFoundException;
import com.tdcollab.spoterly.core.exceptions.UserNotFoundException;
import com.tdcollab.spoterly.repositories.PostRepository;
import com.tdcollab.spoterly.core.services.PostService;
import com.tdcollab.spoterly.repositories.SpotRepository;
import com.tdcollab.spoterly.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SpotRepository spotRepository;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, SpotRepository spotRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.spotRepository = spotRepository;
    }

    @Override
    public PostEntity createPost(PostEntity postEntity) {
        return postRepository.save(postEntity);
    }

    @Override
    public PostEntity findById(UUID id) {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if (postEntity.isEmpty())
            throw new PostNotFoundException("Could not find post with id \"" + id + "\"");
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
        if (!spotRepository.existsById(id))
            throw new SpotNotFoundException("Could not find Spot with id \"" + id + "\"");
        return postRepository.findAllBySpotId(id);
    }

    @Override
    public List<PostEntity> findByUsername(String username) {
        if (!userRepository.existsByUsername(username))
            throw new UserNotFoundException("Could not find User with username \"" + username + "\"");
        return postRepository.findAllByAuthorUsername(username);
    }

    @Override
    public void deletePost(UUID id) {
        PostEntity postEntity = findById(id);
        postRepository.delete(postEntity);
    }
}
