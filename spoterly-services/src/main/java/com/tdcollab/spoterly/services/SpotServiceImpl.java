package com.tdcollab.spoterly.services;

import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.repositories.SpotRepository;
import com.tdcollab.spoterly.core.services.SpotService;
import com.tdcollab.spoterly.core.exceptions.SpotNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SpotServiceImpl implements SpotService {
    private final SpotRepository spotRepository;

    public SpotServiceImpl(SpotRepository spotRepository) {
        this.spotRepository = spotRepository;
    }

    @Override
    public SpotEntity createSpot(SpotEntity spotEntity) {
        return spotRepository.save(spotEntity);
    }

    @Override
    public SpotEntity findById(UUID id) {
        Optional<SpotEntity> spotEntity = spotRepository.findById(id);
        if (spotEntity.isEmpty())
            throw new SpotNotFoundException("Could not find spot with id \"" + id + "\"");
        return spotEntity.get();
    }

    @Override
    public List<SpotEntity> findAll() {
        return StreamSupport.stream(
                spotRepository.findAll().spliterator(),
                false
        ).collect(
                Collectors.toList()
        );
    }

    @Override
    @Transactional
    public List<SpotEntity> findAllByLatitudeAndLongitudeBetween(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude) {
        return spotRepository.findAllByLatitudeBetweenAndLongitudeBetween(minLatitude, maxLatitude, minLongitude, maxLongitude);
    }

    @Override
    public void deleteSpot(UUID id) {
        SpotEntity spotEntity = findById(id);
        spotRepository.delete(spotEntity);
    }
}
