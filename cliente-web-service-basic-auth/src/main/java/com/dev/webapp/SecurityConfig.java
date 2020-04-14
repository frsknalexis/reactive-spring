package com.dev.webapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		final String[] USER_MATCHER = {"/api/clienti/all", "/api/clienti/codice/**", "/api/clienti/nominativo/**",
										"/api/clienti/bollini/**", "/api/clienti/auth"};
		final String[] ADMIN_MATCHER = {"/api/clienti/insert/**", "/api/clienti/elimina/**"};
		
		return http.authorizeExchange()
					.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					.pathMatchers(USER_MATCHER).hasAnyRole("USER")
					.pathMatchers(ADMIN_MATCHER).hasAnyRole("ADMIN")
					.anyExchange().permitAll()
					.and().httpBasic()
					.and().csrf().disable()
					.build();
				
	}
	
	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		return new MapReactiveUserDetailsService(
					User.builder()
						.username("Alexis")
						.password(new BCryptPasswordEncoder().encode("1234"))
						.roles("USER")
						.build(),
					User.builder()
						.username("Admin")
						.password(new BCryptPasswordEncoder().encode("VerySecretPwd"))
						.roles("USER", "ADMIN")
						.build()
				);
	}
}
