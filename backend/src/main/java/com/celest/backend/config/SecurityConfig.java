package com.celest.backend.config;

import com.celest.backend.config.filter.JWTAuthenticationTokenFilter;
import com.celest.backend.config.point.NoAuthenticationHandler;
import com.celest.backend.config.point.NoAuthorizationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JWTAuthenticationTokenFilter jwtAuthenticationTokenFilter,
                                           NoAuthorizationEntryPoint entryPoint) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(((sessionManager)-> sessionManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .authorizeHttpRequests(authorizationRequests -> authorizationRequests
                        //配置访问权限
                        .requestMatchers("/user/account/token", "/user/account/register","/doc.html","/swagger-ui/**","/v3/**","/error").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling((httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                //登录失败异常处理
                                .authenticationEntryPoint(entryPoint)));
//                                .accessDeniedHandler(noAuthenticationHandler)));
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
