package com.example.demo.securityconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                User.withUsername("user")
                        .password(passwordEncoder().encode("123"))
                        .roles("USER")
                        .build(),
                User.withUsername("admin")
                        .password(passwordEncoder().encode("123"))
                        .roles("ADMIN")
                        .build()
        );
    }

    @Bean
    SecurityFilterChain security(HttpSecurity securityy) throws Exception {
        return  securityy
                .formLogin(form -> form
                        .permitAll()
                        .defaultSuccessUrl("/index.html", true) // Redirigir a index.html después de un inicio de sesión exitoso
                )
                .authorizeHttpRequests((auth -> auth.anyRequest().authenticated()))
                .build();
    }


/*
@Bean
public SecurityFilterChain filterchain(HttpSecurity httsecurity) throws Exception {
    return httsecurity
            .csrf().disable().build();
}
*/

}
