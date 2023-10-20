package com.ozius.internship.project.security;

import com.ozius.internship.project.security.jwt.FusedClaimConverter;
import jakarta.annotation.Nullable;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
//this annotation can be put on any config class but this is the best place
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@Nullable CorsRegistry registry) {
                if (registry == null) throw new AssertionError();
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("http://piazza-client-frontend.s3-website.eu-central-1.amazonaws.com:80");
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/authenticate")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/register-client")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/users/{email}")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/images/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/categories/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/products/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/cities")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/products-test")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS, "/**")).permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .headers(header -> header
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                )
                .oauth2ResourceServer(oauth -> oauth
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new FusedClaimConverter())
                        )
                )
                .build();
    }

}
