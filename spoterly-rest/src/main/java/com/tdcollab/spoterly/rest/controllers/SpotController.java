package com.tdcollab.spoterly.rest.controllers;

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

@RestController
@RequestMapping("/spots")
public class SpotController {
    private final Mapper<SpotEntity, SpotDto> spotMapper;
    private final SpotService spotService;
    private final UserMapper userMapper;

    public SpotController(Mapper<SpotEntity, SpotDto> spotMapper, SpotService spotService, UserMapper userMapper) {
        this.spotMapper = spotMapper;
        this.spotService = spotService;
        this.userMapper = userMapper;
    }

    @PostMapping("")
    public ResponseEntity<SpotDto> createSpot(@RequestBody SpotDto spotDto) {
        SpotEntity spotEntity = spotMapper.mapFrom(spotDto);
        SpotEntity savedSpot = spotService.createSpot(spotEntity);
        SpotDto savedSpotDto = spotMapper.mapTo(savedSpot);
        return new ResponseEntity<>(savedSpotDto, HttpStatus.CREATED);
    }

    @GetMapping("")
    public List<SpotDto> getSpots() {
        List<SpotEntity> spotEntities = spotService.findAll();
        return spotEntities
                .stream()
                .map(spotMapper::mapTo)
                .toList();
    }

    @GetMapping("/{id}")
    public SpotDto getSpotById(@PathVariable("id") UUID id) {
        SpotEntity spotEntity = spotService.findById(id);
        return spotMapper.mapTo(spotEntity);
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
