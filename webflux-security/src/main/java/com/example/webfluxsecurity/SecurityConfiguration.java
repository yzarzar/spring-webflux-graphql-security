package com.example.webfluxsecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Map;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Bean
    public MapReactiveUserDetailsService authentication() {
        var users = Map.of(
                        "user", User.withDefaultPasswordEncoder()
                                .username("user")
                                .password("userpassword")
                                .roles("USER")
                                .build(),
                        "admin", User.withDefaultPasswordEncoder()
                                .username("admin")
                                .password("adminpassword")
                                .roles("ADMIN", "USER")
                                .build())
                .values()
                .stream()
                .toList();
        return new MapReactiveUserDetailsService(users);
    }


    @Bean
    public SecurityWebFilterChain authorization(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(ae -> ae.anyExchange().permitAll())
                .httpBasic(Customizer.withDefaults())
                .build();

    }
}
