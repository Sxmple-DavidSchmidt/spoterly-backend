package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.spot.CreateSpotDto;
import com.tdcollab.spoterly.core.dtos.spot.SpotDto;
import com.tdcollab.spoterly.core.dtos.user.UserDto;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.exceptions.SpotNotFoundException;
import com.tdcollab.spoterly.core.mappers.SpotMapper;
import com.tdcollab.spoterly.core.mappers.UserMapper;
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
    private final SpotMapper spotMapper;
    private final SpotService spotService;
    private final UserMapper userMapper;

    public SpotController(SpotMapper spotMapper, SpotService spotService, UserMapper userMapper) {
        this.spotMapper = spotMapper;
        this.spotService = spotService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<SpotDto> createSpot(@RequestBody CreateSpotDto spotDto) {
        SpotEntity spotEntity = spotMapper.entityFromCreateSpotDto(spotDto);
        System.out.println(spotEntity.getId());

        SpotEntity savedSpotEntity = spotService.createSpot(spotEntity);
        SpotDto savedSpotDto = spotMapper.fromSpotEntity(savedSpotEntity);
        return new ResponseEntity<>(savedSpotDto, HttpStatus.CREATED);
    }

    @GetMapping
    public List<SpotDto> getSpots() {
        List<SpotEntity> spotEntities = spotService.findAll();
        return spotEntities
                .stream()
                .map(spotMapper::fromSpotEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public SpotDto getSpotById(@PathVariable("id") String spotIdString) {
        UUID spotId = UUID.fromString(spotIdString);
        SpotEntity spotEntity = spotService.findById(spotId);
        return spotMapper.fromSpotEntity(spotEntity);
    }

    @GetMapping("/{id}/likingUsers")
    public Set<UserDto> getLikingUsers(@PathVariable("id") String spotIdString) {
        UUID spotId = UUID.fromString(spotIdString);
        SpotEntity spotEntity = spotService.findById(spotId);
        return spotEntity.getLikedByUsers()
                .stream()
                .map(userMapper::fromUserEntity)
                .collect(Collectors.toSet());
    }

    @ExceptionHandler(SpotNotFoundException.class)
    public ResponseEntity<String> handleSpotNotFoundException(SpotNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
