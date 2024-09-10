package com.swp.coffeeshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class CaffeShopSecurity {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_name, password, active from login where user_name=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_name, role_name from role where user_name=?");
        return jdbcUserDetailsManager;
    }


//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails son = User.builder()
//                .username("son")
//                .password("{noop}123")
//                .roles("CUSTOMER", "ADMIN")
//                .build();
//
//        UserDetails linh = User.builder()
//                .username("linh")
//                .password("{noop}123")
//                .roles("CUSTOMER")
//                .build();
//
//        return new InMemoryUserDetailsManager(son, linh);
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/").hasAnyRole("CUSTOMER", "MANAGER")
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
