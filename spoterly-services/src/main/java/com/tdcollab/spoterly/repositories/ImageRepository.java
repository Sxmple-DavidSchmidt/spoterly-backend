package com.tdcollab.spoterly.repositories;

import com.tdcollab.spoterly.core.entities.ImageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ImageRepository extends CrudRepository<ImageEntity, UUID> {
}
