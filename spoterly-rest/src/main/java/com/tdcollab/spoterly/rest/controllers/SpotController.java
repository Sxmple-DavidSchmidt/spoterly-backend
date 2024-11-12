package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.CreateSpotDto;
import com.tdcollab.spoterly.core.dtos.SpotDto;
import com.tdcollab.spoterly.core.dtos.UserDto;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.exceptions.SpotNotFoundException;
import com.tdcollab.spoterly.core.mappers.Mapper;
import com.tdcollab.spoterly.core.mappers.impl.UserMapper;
import com.tdcollab.spoterly.core.services.SpotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/spots")
public class SpotController {
    private final Mapper<SpotEntity, CreateSpotDto> spotMapper;
    private final SpotService spotService;
    private final UserMapper userMapper;

    public SpotController(Mapper<SpotEntity, CreateSpotDto> spotMapper, SpotService spotService, UserMapper userMapper) {
        this.spotMapper = spotMapper;
        this.spotService = spotService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<SpotDto> createSpot(@RequestBody CreateSpotDto spotDto) {
        SpotEntity spotEntity = spotMapper.mapFrom(spotDto);
        SpotEntity savedSpot = spotService.createSpot(spotEntity);

        SpotDto spot = new SpotDto(savedSpot.getId(), savedSpot.getName(), savedSpot.getDescription(), savedSpot.getLatitude(), savedSpot.getLongitude(), savedSpot.getCity());

        return new ResponseEntity<>(spot, HttpStatus.CREATED);
    }

    @GetMapping
    public List<SpotDto> getSpots() {
        List<SpotEntity> spotEntities = spotService.findAll();

        return spotEntities.stream().map(entity -> {
            return new SpotDto(entity.getId(), entity.getName(), entity.getDescription(), entity.getLatitude(), entity.getLongitude(), entity.getCity());

        }).toList();
//        return spotEntities
//                .stream()
//                .map(spotMapper::mapTo)
//                .toList();
    }

    @GetMapping("/{id}")
    public SpotDto getSpotById(@PathVariable("id") String id) {

        UUID uuid = UUID.fromString(id);
        SpotEntity spotEntity = spotService.findById(uuid);

        return new SpotDto(spotEntity.getId(), spotEntity.getName(), spotEntity.getDescription(), spotEntity.getLatitude(), spotEntity.getLongitude(), spotEntity.getCity());
    }

    @GetMapping("/{id}/likingUsers")
    public Set<UserDto> getLikingUsers(@PathVariable("id") UUID id) {
        SpotEntity spotEntity = spotService.findById(id);
        return spotEntity.getLikedByUsers()
                .stream()
                .map(userMapper::mapTo)
                .collect(Collectors.toSet());
    }

    @ExceptionHandler(SpotNotFoundException.class)
    public ResponseEntity<String> handleSpotNotFoundException(SpotNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
