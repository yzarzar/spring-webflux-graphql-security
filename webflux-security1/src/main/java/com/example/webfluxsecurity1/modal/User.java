package com.example.webfluxsecurity1.modal;

import com.example.webfluxsecurity1.modal.input.UserInput;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @Id
    private Integer userId;
    private String name;
    private String password;
    private Role role;

    public User(UserInput input) {
        this.name = input.getName();
        this.password = input.getPassword();
        this.role = input.getRole();
    }

}
