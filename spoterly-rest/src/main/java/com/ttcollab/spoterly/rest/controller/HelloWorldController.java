package com.ttcollab.spoterly.rest.controller;

import com.ttcollab.spoterly.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    private final UserService userService;

    public HelloWorldController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }
}
