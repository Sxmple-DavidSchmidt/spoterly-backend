package com.tdcollab.spoterly.core.services;

import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.entities.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface PostService {
    PostEntity createPost(PostEntity postEntity);
    PostEntity findById(UUID id);
    List<PostEntity> findAll();
    Set<UserEntity> findLikingUsers(UUID id);
}
