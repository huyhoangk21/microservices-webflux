package io.huyhoang.userservice.service;

import io.huyhoang.userservice.config.JwtConfig;
import io.huyhoang.userservice.dto.LoginRequest;
import io.huyhoang.userservice.dto.SignupRequest;
import io.huyhoang.userservice.dto.UserResponse;
import io.huyhoang.userservice.entity.User;
import io.huyhoang.userservice.exception.ResourceAlreadyExistsException;
import io.huyhoang.userservice.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashSet;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;


    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
    }

    @Transactional
    public Mono<UserResponse> signup(SignupRequest signupRequest) {
        return userRepository.existsByUsername(signupRequest.getUsername())
                .flatMap(value -> value ?
                        Mono.error(new ResourceAlreadyExistsException("Username already exists")) :
                        userRepository.existsByEmail(signupRequest.getEmail()))
                .flatMap(value -> value ?
                        Mono.error(new ResourceAlreadyExistsException("Email already exists")) :
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
                    String token = jwtConfig.getPrefix() + " " + Jwts.builder()
                            .setSubject(userResponse.getUserId().toString())
                            .setIssuedAt(new Date())
                            .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getExpiry()))
                            .signWith(SignatureAlgorithm.HS512, jwtConfig.getSecretKey())
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
