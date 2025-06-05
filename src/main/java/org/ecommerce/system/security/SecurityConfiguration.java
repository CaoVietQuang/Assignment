package org.ecommerce.system.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        "/api/v1/auth/**",
                                        "/api/v1/category/create",
                                        "/api/v1/category/update/{id}",
                                        "/api/v1/category/delete/{id}",
                                        "/api/v1/category/getById/{id}",
                                        "/api/v1/category/getAllCategories",

                                        "/api/v1/publishers/create",
                                        "/api/v1/publishers/update/{id}",
                                        "/api/v1/publishers/delete/{id}",
                                        "/api/v1/publishers/getById/{id}",
                                        "/api/v1/publishers/getAllPublishers",

                                        "/api/v1/products/create",
                                        "/api/v1/products/update/{id}",
                                        "/api/v1/products/delete/{id}",
                                        "/api/v1/products/getById/{id}",
                                        "/api/v1/products/all",
                                        "/api/v1/products/priceRange/",
                                        "/api/v1/products/Products/{publisherId}",
                                        "/api/v1/products/topRated"


                                ).permitAll()
                                .anyRequest().authenticated()

                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring().
                requestMatchers("/actuator/**", "/v3/**", "/webjars/**", "/swagger-ui*/*swagger-initializer.js", "/swagger-ui*/**");
    }
}