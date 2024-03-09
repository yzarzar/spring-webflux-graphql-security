package com.example.webfluxsecurity1.repository;

import com.example.webfluxsecurity1.modal.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
    Mono<User> findByName(Object username);
}