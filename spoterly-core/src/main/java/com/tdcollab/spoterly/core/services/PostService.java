package com.tdcollab.spoterly.core.services;

import com.tdcollab.spoterly.core.entities.PostEntity;

import java.util.List;
import java.util.UUID;

public interface PostService {
    PostEntity createPost(PostEntity postEntity);
    PostEntity findById(UUID id);
    List<PostEntity> findAll();
    void deletePost(UUID id);
}
