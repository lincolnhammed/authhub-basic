package dev.lincolnsilva.authhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            CustomAuthenticationEntryPoint authenticationEntryPoint,
            CustomAccessDeniedHandler accessDeniedHandler
    ) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173",
                        "http://localhost:8087")
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    @Bean
    public RoleHierarchy roleHierarchy() {
//        Assim, ADMIN pode acessar tudo que GERENTE e AUDITOR
//        podem, mas GERENTE não ganha automaticamente as permissões de AUDITOR.

//        return RoleHierarchyImpl.fromHierarchy("""
//        ROLE_ADMIN > ROLE_GERENTE
//        ROLE_ADMIN > ROLE_AUDITOR
//        ROLE_GERENTE > ROLE_USER
//        """);
        return RoleHierarchyImpl.fromHierarchy("""
        ROLE_ADMIN > ROLE_GERENTE
        ROLE_GERENTE > ROLE_SUPERVISOR
        ROLE_SUPERVISOR > ROLE_USER
        """);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .logout(logout -> logout.disable())
                .authorizeHttpRequests(auth -> auth
                        // Qualquer pessoa pode criar uma conta
                        .requestMatchers(HttpMethod.POST, "/users").permitAll()

                        // Qualquer pessoa pode fazer login
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()

                        .requestMatchers(HttpMethod.POST, "/auth/refresh").permitAll()

                        .requestMatchers(HttpMethod.POST, "/logout").permitAll()
                        // Área exclusiva de ADMIN
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // Área de GERENTE e superiores
                        .requestMatchers("/gerente/**")
                        .hasRole("GERENTE")

                        // Área de USER e superiores
                        .requestMatchers("/user/**")
                        .hasRole("USER")

                        // Todo o resto precisa estar autenticado
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}