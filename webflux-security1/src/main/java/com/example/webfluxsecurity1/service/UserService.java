package com.example.webfluxsecurity1.service;

import com.example.webfluxsecurity1.modal.User;
import com.example.webfluxsecurity1.modal.input.UserInput;
import com.example.webfluxsecurity1.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> addUser(UserInput input) {
        return this.userRepository.save(new User(input));
    }

    public Mono<User> getUserById(Integer id) {
        return this.userRepository.findById(id);
    }

    public Flux<User> getAllUsers() {
        return this.userRepository.findAll();
    }
}
