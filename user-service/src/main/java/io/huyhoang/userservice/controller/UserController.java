package io.huyhoang.userservice.controller;

import io.huyhoang.userservice.dto.LoginRequest;
import io.huyhoang.userservice.dto.SignupRequest;
import io.huyhoang.userservice.dto.UserResponse;
import io.huyhoang.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/signup")
    public Mono<UserResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return userService.signup(signupRequest);
    }

    @PostMapping(value = "/login")
    public Mono<ResponseEntity<UserResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping(value = "/{userId}")
    public Mono<UserResponse> getById(@PathVariable("userId") UUID userId) {
        return userService.findById(userId);
    }



}
