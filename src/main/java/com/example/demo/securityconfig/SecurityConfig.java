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
        return securityy.csrf().disable()
                .formLogin(form -> form
                        .permitAll()
                        .defaultSuccessUrl("/prueba.html", true)
                        .successHandler((request, response, authentication) -> {
                            // Obtener el rol del usuario autenticado
                            String role = authentication.getAuthorities().iterator().next().getAuthority();
                            System.out.println("Rol del usuario autenticado: " + role);

                            // Redirigir a una página según el rol (opcional)
                            if ("ROLE_ADMIN".equals(role)) {
                                response.sendRedirect("/index.html");
                            } else {
                                response.sendRedirect("/prueba.html");
                            }
                        })
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


