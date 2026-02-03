package com.example.EMS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class securityConfig {
	@Bean
	public PasswordEncoder passwordEncoder()
	{
			return new BCryptPasswordEncoder();
	}
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // disable CSRF for REST APIs
            .csrf(csrf -> csrf.disable())

            // allow all requests
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers(
            				 "/swagger-ui/**",
            			        "/v3/api-docs/**",
            			        "/swagger-ui.html"
                ).permitAll()
            .anyRequest().permitAll()
            )

            // disable default login form
            .formLogin(form -> form.disable())

            // disable basic auth popup
            .httpBasic(basic -> basic.disable());

        return http.build();
    }

 
}
