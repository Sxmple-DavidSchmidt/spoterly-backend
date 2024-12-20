package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.dtos.spot.CreateSpotDto;
import com.tdcollab.spoterly.core.dtos.spot.MinimalSpotDto;
import com.tdcollab.spoterly.core.dtos.user.MinimalUserDto;
import com.tdcollab.spoterly.core.entities.SpotEntity;
import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.exceptions.SpotNotFoundException;
import com.tdcollab.spoterly.core.exceptions.UserNotFoundException;
import com.tdcollab.spoterly.core.mappers.SpotMapper;
import com.tdcollab.spoterly.core.mappers.UserMapper;
import com.tdcollab.spoterly.core.services.SpotService;
import com.tdcollab.spoterly.core.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("api/spots")
public class SpotController {
    private final SpotMapper spotMapper;
    private final SpotService spotService;
    private final UserMapper userMapper;
    private final UserService userService;

    public SpotController(SpotMapper spotMapper, SpotService spotService, UserMapper userMapper, UserService userService) {
        this.spotMapper = spotMapper;
        this.spotService = spotService;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @PostMapping("/{username}/createSpot")
    @PreAuthorize("@userSecurity.isCurrentUserOrAdmin(#username)")
    public ResponseEntity<MinimalSpotDto> createSpot(@RequestBody CreateSpotDto spotDto, @PathVariable("username") String username) {
        UserEntity userEntity = userService.findByUsername(username);
        SpotEntity spotEntity = spotMapper.entityFromCreateSpotDto(spotDto);
        spotEntity.setAuthor(userEntity);
        SpotEntity savedSpotEntity = spotService.createSpot(spotEntity);
        MinimalSpotDto savedMinimalSpotDto = spotMapper.minimalFromSpotEntity(savedSpotEntity);
        return new ResponseEntity<>(savedMinimalSpotDto, HttpStatus.CREATED);
    }

    @GetMapping()
    public Set<MinimalSpotDto> getAllSpotsByAttribute(@RequestParam Map<String, String> query) {
        double minLat = -Double.MAX_VALUE;
        if (query.containsKey("minLatitude"))
            minLat = Double.parseDouble(query.get("minLatitude"));

        double maxLat = Double.MAX_VALUE;
        if (query.containsKey("maxLatitude"))
            maxLat = Double.parseDouble(query.get("maxLatitude"));

        double minLon = -Double.MAX_VALUE;
        if (query.containsKey("minLongitude"))
            minLon = Double.parseDouble(query.get("minLongitude"));

        double maxLon = Double.MAX_VALUE;
        if (query.containsKey("maxLongitude"))
            maxLon = Double.parseDouble(query.get("maxLongitude"));

        return spotService.findAllByLatitudeAndLongitudeBetween(minLat, maxLat, minLon, maxLon)
                .stream()
                .map(spotMapper::minimalFromSpotEntity)
                .collect(Collectors.toSet());
    }

    @GetMapping("/{id}")
    public MinimalSpotDto getSpotById(@PathVariable("id") String spotIdString) {
        UUID spotId = UUID.fromString(spotIdString);
        SpotEntity spotEntity = spotService.findById(spotId);
        return spotMapper.minimalFromSpotEntity(spotEntity);
    }

    @GetMapping("/{id}/likingUsers")
    public Set<MinimalUserDto> getLikingUsers(@PathVariable("id") String spotIdString) {
        UUID spotId = UUID.fromString(spotIdString);
        SpotEntity spotEntity = spotService.findById(spotId);
        return spotEntity.getLikedByUsers()
                .stream()
                .map(userMapper::minimalFromUserEntity)
                .collect(Collectors.toSet());
    }

    @ExceptionHandler(SpotNotFoundException.class)
    public ResponseEntity<String> handleSpotNotFoundException(SpotNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
