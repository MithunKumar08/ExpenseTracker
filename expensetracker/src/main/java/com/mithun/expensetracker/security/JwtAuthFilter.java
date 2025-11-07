package com.mithun.expensetracker.security;

import com.mithun.expensetracker.entity.User;
import com.mithun.expensetracker.repo.UserRepo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    UserRepo userRepo;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        if (path.equals("/auth/v1/register") || path.equals("/auth/v1/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String requestTokenHeader = request.getHeader("Authorization");

        try {
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer")) {
                throw new JwtException("Token is Miissing or Invalid");
            }

            String token = requestTokenHeader.split("Bearer ")[1];
            String getUserName = jwtUtils.getUserNameFromToken(token);

            if (getUserName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepo.findByUserName(getUserName).orElseThrow();
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

            filterChain.doFilter(request, response);
        }catch (JwtException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"" + "Token is Miissing or Invalid" + "\"}");

        }
           }
}
