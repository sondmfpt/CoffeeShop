package com.swp.coffeeshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CaffeShopSecurity {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails son = User.builder()
                .username("son")
                .password("{noop}123")
                .roles("CUSTOMER", "ADMIN")
                .build();

        UserDetails linh = User.builder()
                .username("linh")
                .password("{noop}123")
                .roles("CUSTOMER")
                .build();

        return new InMemoryUserDetailsManager(son, linh);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").hasAnyRole("ADMIN", "CUSTOMER")
                                .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/showLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll()
                )
                .logout(configurer ->
                        configurer.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer
                                .accessDeniedPage("/access-denied")
                );
        return http.build();
    }
}
