package com.tdcollab.spoterly.core.services;

import com.tdcollab.spoterly.core.entities.SpotEntity;

import java.util.List;
import java.util.UUID;

public interface SpotService {
    SpotEntity createSpot(SpotEntity spotEntity);
    SpotEntity findById(UUID id);
    List<SpotEntity> findAll();
    List<SpotEntity> findAllByLatitudeAndLongitudeBetween(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude);
    void deleteSpot(UUID id);
}
