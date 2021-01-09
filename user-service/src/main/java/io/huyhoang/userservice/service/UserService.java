package io.huyhoang.userservice.service;

import io.huyhoang.userservice.dto.LoginRequest;
import io.huyhoang.userservice.dto.SignupRequest;
import io.huyhoang.userservice.dto.UserResponse;
import io.huyhoang.userservice.entity.User;
import io.huyhoang.userservice.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashSet;

@Service
public class UserService {

    Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public Mono<UserResponse> signup(SignupRequest signupRequest) {
        return userRepository.existsByUsername(signupRequest.getUsername())
                .flatMap(value -> value ?
                        Mono.error(RuntimeException::new) :
                        userRepository.existsByEmail(signupRequest.getEmail()))
                .flatMap(value -> value ?
                        Mono.error(RuntimeException::new) :
                        userRepository.save(
                                new User(signupRequest.getUsername(),
                                         signupRequest.getEmail(),
                                         passwordEncoder.encode(signupRequest.getPassword())))
                .flatMap(this::convertDTO));

    }

    public Mono<ResponseEntity<UserResponse>> login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword(),
                        new HashSet<>()));

        return userRepository.findByEmail(loginRequest.getEmail())
                .flatMap(this::convertDTO)
                .flatMap(userResponse -> {
                    String token = "Bearer " + Jwts.builder()
                            .setSubject(userResponse.getUserId().toString())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                            .signWith(SignatureAlgorithm.HS512, "fadsfasdfsdfasdfsdfsfsdfasdf")
                            .compact();
                    return Mono.just(ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(userResponse));
                });
    }


    private Mono<UserResponse> convertDTO(User user) {
        return Mono.just(new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()));
    }
}
