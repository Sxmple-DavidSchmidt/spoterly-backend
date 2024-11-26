package com.tdcollab.spoterly.rest.controllers;

import com.tdcollab.spoterly.core.entities.Role;
import com.tdcollab.spoterly.core.entities.UserEntity;
import com.tdcollab.spoterly.core.exceptions.*;
import com.tdcollab.spoterly.core.services.ImageService;
import com.tdcollab.spoterly.core.services.PostService;
import com.tdcollab.spoterly.core.services.SpotService;
import com.tdcollab.spoterly.core.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/admin")
public class AdminController {
    private final UserService userService;
    private final PostService postService;
    private final SpotService spotService;
    private final ImageService imageService;

    public AdminController(UserService userService, PostService postService, SpotService spotService, ImageService imageService) {
        this.userService = userService;
        this.postService = postService;
        this.spotService = spotService;
        this.imageService = imageService;
    }

    @PostMapping("/deleteUser/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String username) {
        UserEntity userEntity = userService.findByUsername(username);
        if (userEntity.getRole() != Role.ADMIN) {
            userService.deleteUser(userEntity.getUsername());
            return ResponseEntity.ok("User " + userEntity.getUsername() + " deleted.");
        }

        return new ResponseEntity<>("Cannot delete admin user.", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/updateUserRole/{username}/{role}")
    public ResponseEntity<String> updateUserRole(@PathVariable("username") String username, @PathVariable("role") String roleString) {
        Role role;
        if ("USER".equalsIgnoreCase(roleString)) {
            role = Role.USER;
        } else if ("ADMIN".equalsIgnoreCase(roleString)) {
            role = Role.ADMIN;
        } else {
            throw new RoleNotFoundException("Role \"" + roleString + "\" does not match any valid role.");
        }

        userService.setRole(username, role);
        return ResponseEntity.ok("User \"" + username + "\" now has role " + role.name());
    }

    @PostMapping("/deletePost/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") String postIdString) {
        UUID postId = UUID.fromString(postIdString);
        postService.deletePost(postId);
        return ResponseEntity.ok("Post with id \"" + postId + "\" deleted.");
    }

    @PostMapping("/deleteSpot/{id}")
    public ResponseEntity<String> deleteSpot(@PathVariable("id") String spotIdString) {
        UUID spotId = UUID.fromString(spotIdString);
        spotService.deleteSpot(spotId);
        return ResponseEntity.ok("Spot with id \"" + spotId + "\" deleted.");
    }

    @PostMapping("/deleteImage/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable("id") String imageIdString) {
        UUID imageId = UUID.fromString(imageIdString);
        imageService.deleteImage(imageId);
        return ResponseEntity.ok("Image with id \"" + imageId + "\" deleted.");
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFoundException(RoleNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(SpotNotFoundException.class)
    public ResponseEntity<String> handleSpotNotFoundException(SpotNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<String> handlePostNotFoundException(PostNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
