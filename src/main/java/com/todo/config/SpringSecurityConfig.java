package com.todo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public static PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
     
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) {
		
		http.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth
//						/*
//						 * .requestMatchers(HttpMethod.PUT,"/api/**").hasRole("ADMIN")
//						 * .requestMatchers(HttpMethod.POST,"/api/**").hasRole("ADMIN")
//						 * .requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN")
//						 * .requestMatchers(HttpMethod.GET,"/api/**").hasAnyRole("ADMIN","USER")
//						 */
				  // .requestMatchers("/api/**").permitAll()
				.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
		return configuration.getAuthenticationManager();
	}
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user1 = User.builder()
//				              .username("kaushal")
//				              .password(encoder().encode("kaushal"))
//				              .roles("USER")
//				              .build();
//		
//		UserDetails admin = User.builder()
//				            .username("admin")
//				            .password(encoder().encode("admin"))
//				            .roles("ADMIN")
//				            .build();
//		
//		return new InMemoryUserDetailsManager(user1,admin);
//	}
}
