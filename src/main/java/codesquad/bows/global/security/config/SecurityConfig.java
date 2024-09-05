package codesquad.bows.global.security.config;

import codesquad.bows.global.security.filter.CustomAuthenticationFailureHandler;
import codesquad.bows.global.security.filter.CustomAuthenticationSuccessHandler;
import codesquad.bows.global.security.filter.JwtAuthorizationFilter;
import codesquad.bows.global.security.filter.JwtLoginFilter;
import codesquad.bows.global.security.filter.JwtAuthenticationEntryPoint;
import codesquad.bows.global.security.jwt.JwtTokenProvider;
import codesquad.bows.global.security.jwt.service.TokenService;
import codesquad.bows.global.security.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint entryPoint;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final TokenService tokenService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager를 빈으로 제공
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        CustomAuthenticationFailureHandler authFailureHandler = new CustomAuthenticationFailureHandler(handlerExceptionResolver);
        CustomAuthenticationSuccessHandler authSuccessHandler = new CustomAuthenticationSuccessHandler(jwtTokenProvider, tokenService);

        // JwtLoginFilter에 AuthenticationManager 주입
        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager, authSuccessHandler, authFailureHandler);
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter(jwtTokenProvider, customUserDetailsService);

        httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/members/register", "/api/members/login", "/api/members/check-userId").permitAll()
                        .requestMatchers("/api/members/**").hasRole("USER")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(auth -> auth.disable())
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class) // 로그인 필터 전에 jwt토큰 확인
                .addFilter(jwtLoginFilter) // JWT 필터 추가
                .exceptionHandling(handle -> handle.authenticationEntryPoint(entryPoint));

        return httpSecurity.build();
    }
}
