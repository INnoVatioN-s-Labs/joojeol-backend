package com.assignment.joojeolbackend.security.jwt;

import com.assignment.joojeolbackend.domain.user.User;
import com.assignment.joojeolbackend.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveToken(request);

        if (token != null && jwtTokenProvider.validateToken(token)) {
            String userKeyStr = jwtTokenProvider.getPayload(token);
            try {
                Long tossUserKey = Long.parseLong(userKeyStr);
                User user = userRepository.findByTossUserKey(tossUserKey).orElse(null);

                if (user != null) {
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (NumberFormatException ignored) {
                // Invalid token payload
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
