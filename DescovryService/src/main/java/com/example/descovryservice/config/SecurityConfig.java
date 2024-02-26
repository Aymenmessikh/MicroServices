/*package com.example.descovryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private String username;
    private String password;
    @Bean
    protected InMemoryUserDetailsManager configAuthentication() {

        UserDetails users;
        GrantedAuthority UserAuthority;
        UserAuthority=new SimpleGrantedAuthority("uSER");
        UserDetails user= new User(username, password, Collections.singleton(UserAuthority));
        return new InMemoryUserDetailsManager(user);
    }
    @Bean
    public void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable()
                .authorizeHttpRequests().anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }
}
*/