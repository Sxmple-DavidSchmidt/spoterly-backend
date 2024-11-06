package com.tdcollab.spoterly.repositories;

import com.tdcollab.spoterly.core.entities.PostEntity;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, UUID> {
    @Query(
            "SELECT p FROM PostEntity p " +
            "JOIN p.likedByUsers u " +
            "WHERE u.username = :username")
    List<PostEntity> findLikedPostsByUsername(@Param("username") String username);
}