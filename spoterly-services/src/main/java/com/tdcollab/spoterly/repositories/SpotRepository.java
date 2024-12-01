package com.tdcollab.spoterly.repositories;

import com.tdcollab.spoterly.core.entities.SpotEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpotRepository extends CrudRepository<SpotEntity, UUID> {
    List<SpotEntity> findAllByLatitudeBetweenAndLongitudeBetween(double latitudeAfter, double latitudeBefore, double longitudeAfter, double longitudeBefore);
}
