package com.example.webfluxsecurity1.service;

import com.example.webfluxsecurity1.modal.Customer;
import com.example.webfluxsecurity1.repository.CustomerRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class CrmService {

    private final CustomerRepository customerRepository;

    public CrmService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PreAuthorize("hasRole('USER')")
    public Mono<Customer> getCustomerById(Integer id) {
        return this.customerRepository.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Customer> insert(String name) {
        return this.customerRepository.save(new Customer(name));
    }
}
