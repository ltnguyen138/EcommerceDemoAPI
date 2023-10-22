package com.ecommerce.configurations;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ecommerce.filter.JwtTokenFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	@Autowired
	JwtTokenFilter jwtTokenFilter;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception{
		
		http
			.csrf(AbstractHttpConfigurer::disable)
			.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeHttpRequests(request ->{
				request
					.requestMatchers("api/v1/user","api/v1/user/login").permitAll()
					.requestMatchers(HttpMethod.POST,"api/v1/user/**").permitAll()
					.requestMatchers(HttpMethod.PUT,"api/v1/user/**").permitAll()
					.requestMatchers(HttpMethod.DELETE,"api/v1/user/**").hasRole("admin")
					.requestMatchers(HttpMethod.GET,"api/v1/products/**").permitAll()
					.requestMatchers(HttpMethod.POST,"api/v1/products/**").hasRole("admin")
					.requestMatchers(HttpMethod.PUT,"api/v1/products/**").hasRole("admin")
					.requestMatchers(HttpMethod.DELETE,"api/v1/products/**").hasRole("admin")
					.requestMatchers(HttpMethod.POST,"api/v1/category/**").hasRole("admin")
					.requestMatchers(HttpMethod.PUT,"api/v1/category/**").hasRole("admin")
					.requestMatchers(HttpMethod.DELETE,"api/v1/category/**").hasRole("admin")
					.requestMatchers(HttpMethod.GET,"api/v1/category/**").permitAll()
					.requestMatchers("api/v1/orders/**").permitAll()
					.requestMatchers("api/v1/carts/**").permitAll()
					
					.anyRequest().permitAll();
			})
		;
		
		return http.build();
	}
}
