package com.celest.backend.config;

import com.celest.backend.config.filter.JWTAuthenticationTokenFilter;
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
                        .requestMatchers("/api/user/account/token", "/api/user/account/register","/doc.html",
                                "/swagger-ui/**","/v3/**","/error","/websocket/**","/match/startGame",
                                "/botMove/setDirection","/api/acwing/web/login/applyCode",
                                "/api/acwing/web/login/redirect")
//                        .access(IpAddressAuthorizationManager.hasIpAddress("127.0.0.1")) 配置发访问IP的地址限制
                        .permitAll()
                        .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling((httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                //登录失败异常处理
                                .authenticationEntryPoint(entryPoint)));
//                                .accessDeniedHandler(noAuthenticationHandler)));
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedOrigins(Collections.singletonList("*"));
//        configuration.setAllowedMethods(Collections.singletonList("*"));
//        configuration.setAllowedHeaders(Collections.singletonList("*"));
//        configuration.setMaxAge(Duration.ofHours(1));
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
    // 配置访问路径,防止springSecurity拦截websocket路径
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer(){
//        return (web) -> web.ignoring().requestMatchers("/websocket/**");
//    }

}
