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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.*;

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
                .zipWith(userRepository.existsByEmail(signupRequest.getEmail()))
                .flatMap(result -> {
                    List<String> errors = new LinkedList<>();
                    if (result.getT1()) errors.add("Username already exists");
                    if (result.getT2()) errors.add("Email already exists");
                    return errors.isEmpty() ? Mono.just(signupRequest) : Mono.error(new ResourceAlreadyExistsException(errors));
                })
                .then(userRepository.save(
                        new User(signupRequest.getUsername(),
                                signupRequest.getEmail(),
                                passwordEncoder.encode(signupRequest.getPassword()))
                ))
                .flatMap(this::convertDTO);
    }

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public Mono<UserResponse> findById(UUID userId) {
        return userRepository.findById(userId)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Username not found")))
                .flatMap(this::convertDTO);
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
