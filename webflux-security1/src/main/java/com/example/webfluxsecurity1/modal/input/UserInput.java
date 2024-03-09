package com.example.webfluxsecurity1.modal.input;

import com.example.webfluxsecurity1.modal.Role;
import lombok.Data;

@Data
public class UserInput {

    private String name;
    private String password;
    private Role role;
}
