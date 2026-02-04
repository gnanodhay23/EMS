package com.example.EMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.EMS.Service.employeeService;

@Configuration
@EnableWebSecurity
public class securityConfig {
	@Autowired
	private jwtAuthFilter jwtAuthFilter;
	@Bean
	public PasswordEncoder passwordEncoder()
	{
			return new BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationManager(
	        AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
//		String path = request.getServletPath();
//
//		if (path.equals("/login") || path.equals("/register")) {
//		    filterChain.doFilter(request, response);
//		    return;
//		}
        http
        	
        // 🔥 REQUIRED FOR JWT
        .sessionManagement(sm ->
            sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // disable CSRF for REST APIs
            .csrf(csrf -> csrf.disable())

            // allow all requests
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/login","/register",
            				 "/swagger-ui/**",
            			        "/v3/api-docs/**",
            			        "/swagger-ui.html"
                ).permitAll()
            .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

            // disable default login form
            .formLogin(form -> form.disable())

            // disable basic auth popup
            .httpBasic(basic -> basic.disable());

        return http.build();
    }
	@Bean
	public DaoAuthenticationProvider authenticationProvider(employeeService userService) {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	    provider.setUserDetailsService(userService);
	    provider.setPasswordEncoder(passwordEncoder());
	    return provider;
	}


 
}
