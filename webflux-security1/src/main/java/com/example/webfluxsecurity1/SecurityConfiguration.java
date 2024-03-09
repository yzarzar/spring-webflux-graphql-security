//package com.example.webfluxsecurity1;
//
//import com.example.webfluxsecurity1.repository.UserRepository;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//import java.util.Map;
//
//@Configuration
//@EnableReactiveMethodSecurity
//public class SecurityConfiguration {
//
//    private final UserRepository userRepository;
//
//    public SecurityConfiguration(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Bean
//    public MapReactiveUserDetailsService authentication() {
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        var users = Map.of(
//                        "user", User.withDefaultPasswordEncoder()
//                                .username("user")
//                                .password("userpassword")
//                                .roles("USER")
//                                .build(),
//                        "admin", User.withDefaultPasswordEncoder()
//                                .username("admin")
//                                .password("adminpassword")
//                                .roles("ADMIN")
//                                .build())
//                .values()
//                .stream()
//                .toList();
//        return new MapReactiveUserDetailsService(users);
//    }
//
//    @Bean
//    public SecurityWebFilterChain authorization(ServerHttpSecurity http) {
//        return http
//                .csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(ae -> ae.anyExchange().permitAll())
//                .httpBasic(Customizer.withDefaults())
//                .build();
//
//    }
//}
package com.example.webfluxsecurity1;

import com.example.webfluxsecurity1.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;

@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    private final UserRepository userRepository;

    public SecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return username -> userRepository.findByName(username)
                .map(user -> User
                        .withUsername(user.getName())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .roles(String.valueOf(user.getRole()))
                        .build())
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException("User not found"))));
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
