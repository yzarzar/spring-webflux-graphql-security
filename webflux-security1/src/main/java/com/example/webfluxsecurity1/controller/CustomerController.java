package com.example.webfluxsecurity1.controller;

import com.example.webfluxsecurity1.modal.Customer;
import com.example.webfluxsecurity1.service.CrmService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

    private final CrmService crm;

    public CustomerController(CrmService crm) {
        this.crm = crm;
    }

    @MutationMapping
    public Mono<Customer> insert(@Argument String name) {
        return this.crm.insert(name);
    }

    @QueryMapping
    public Mono<Customer> customerById(@Argument Integer id) {
        return this.crm.getCustomerById(id);
    }
}
