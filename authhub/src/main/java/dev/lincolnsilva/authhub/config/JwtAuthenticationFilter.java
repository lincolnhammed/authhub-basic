package dev.lincolnsilva.authhub.config;

import dev.lincolnsilva.authhub.model.User;
import dev.lincolnsilva.authhub.repository.UserRepository;
import dev.lincolnsilva.authhub.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(
            TokenService tokenService,
            UserRepository userRepository
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if (authorization != null && authorization.startsWith("Bearer ")) {

            String token = authorization.substring(7);

            boolean valid = tokenService.validateToken(token);

            if (valid) {
                String subject = tokenService.getSubject(token);

                UUID userId = UUID.fromString(subject);

                User user = userRepository.findById(userId).orElse(null);

                if (user != null) {
                    String role = user.getRole();

                    var authority = new SimpleGrantedAuthority("ROLE_" + role);

                    var authentication = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            List.of(authority)
                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);
                }
            }


        }
        filterChain.doFilter(request, response);
    }
}