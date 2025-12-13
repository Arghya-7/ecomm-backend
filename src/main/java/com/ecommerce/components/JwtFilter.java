package com.ecommerce.components;

import com.ecommerce.service.UserService;
import com.ecommerce.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserService userService;


    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String userName = null;

        // 2. Validate header format "Bearer <token>"
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                // 3. Extract username from JWT
                userName = jwtUtil.extractUsername(token);
            } catch (Exception ex) {
                System.out.println("Invalid JWT Token: " + ex.getMessage());
            }
        }

        // 4. Authenticate only if username exists & user not already authenticated
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 5. Load user from custom repository through CustomUserDetailsService
            UserDetails userDetails = userService.loadUserByUsername(userName);

            // 6. Validate token (signature + expiry)
            if (!jwtUtil.validateToken(token, userDetails.getUsername())) {

                // 7. Create authentication object
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                // 8. Store authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 9. Continue filter chain
        filterChain.doFilter(request, response);
    }
}
