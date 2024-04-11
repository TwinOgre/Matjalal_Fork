package com.proj.Matjalal.global.security;


import com.proj.Matjalal.global.security.filter.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class APISecurityConfig {
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/api/*/members/login").permitAll() // 로그인은 누구나 가능
                                .requestMatchers("/api/*/members/logout").permitAll()
                                .requestMatchers("/api/*/articles").permitAll() //전체 글 보기는 누구나 가능
                                .requestMatchers("/api/*/articles/*").permitAll() //글 상세 보기 누구나 가능
                                .requestMatchers("/api/*/ingredients").permitAll() //전체 재료 보기는 누구나 가능
                                .requestMatchers("/api/*/ingredients/*").permitAll() // 재료 상세 보기는 누구나 가능
                                .requestMatchers("/api/*/ingredients/type/*").permitAll() // 재료 상세 보기는 누구나 가능
                                .anyRequest().authenticated() // 나머지는 인가 처리된 사용자만 가능
                )
                .cors(
                        cors -> cors.disable()
                ) //cors 설정
                .csrf(
                        csrf -> csrf
                                .disable()
                ) // csrf 토큰 끄기
                .httpBasic(
                        httpBasic -> httpBasic.disable()
                ) //httpBasic 로그인 방식 끄기
                .formLogin(
                        formLogin -> formLogin.disable()
                ) // 폼 로그인 방식 끄기
                .sessionManagement(
                        sessionManagement -> sessionManagement.sessionCreationPolicy(STATELESS)
                ) // 세션 끄기
                .addFilterBefore(
                        jwtAuthorizationFilter, // 액세스 토큰을 이용한 로그인 처리
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }
}