package com.tdcollab.spoterly.repositories;

import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpotRepository extends CrudRepository<SpotEntity, UUID> {
}
