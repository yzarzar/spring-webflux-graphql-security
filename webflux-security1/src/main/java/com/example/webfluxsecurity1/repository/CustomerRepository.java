package com.example.webfluxsecurity1.repository;

import com.example.webfluxsecurity1.modal.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
}
