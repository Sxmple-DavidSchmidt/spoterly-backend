package com.tdcollab.spoterly.core.services;

import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.entities.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface SpotService {
    SpotEntity createSpot(SpotEntity spotEntity);
    SpotEntity findById(UUID id);
    List<SpotEntity> findAll();
    Set<UserEntity> findLikingUsers(UUID id);
}
