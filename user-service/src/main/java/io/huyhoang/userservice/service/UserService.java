package io.huyhoang.userservice.service;

import io.huyhoang.userservice.dto.SignupRequest;
import io.huyhoang.userservice.dto.UserResponse;
import io.huyhoang.userservice.entity.User;
import io.huyhoang.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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


    private Mono<UserResponse> convertDTO(User user) {
        return Mono.just(new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()));
    }


}
