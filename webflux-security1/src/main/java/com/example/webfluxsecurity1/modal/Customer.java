package com.example.webfluxsecurity1.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("customers")
public class Customer {

    @Id
    private Integer customerId;
    private String name;

    public Customer(String name) {
        this.name = name;
    }
}
