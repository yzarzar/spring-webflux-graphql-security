package com.example.webfluxsecurity1.controller;

import com.example.webfluxsecurity1.modal.User;
import com.example.webfluxsecurity1.modal.input.UserInput;
import com.example.webfluxsecurity1.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @MutationMapping
    public Mono<User> createUser(@Argument UserInput input) {
        return this.userService.addUser(input);
    }

    @QueryMapping
    public Mono<User> user(@Argument Integer id) {
        return this.userService.getUserById(id);
    }
}
