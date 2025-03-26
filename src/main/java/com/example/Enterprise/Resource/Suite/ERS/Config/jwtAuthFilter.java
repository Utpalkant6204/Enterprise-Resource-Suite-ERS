package com.example.Enterprise.Resource.Suite.ERS.Config;

import com.example.Enterprise.Resource.Suite.ERS.Services.Impl.JwtService;
import com.example.Enterprise.Resource.Suite.ERS.Services.Impl.MyUserDetailsService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class jwtAuthFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final MyUserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Autowired
    public jwtAuthFilter(ObjectMapper objectMapper, MyUserDetailsService userDetailsService, JwtService jwtService) {
        this.objectMapper = objectMapper;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (request.getRequestURI().equals("/login")) {
            chain.doFilter(request, response);
            return;
        }

        String requestBody = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);

        if (requestBody.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(requestBody);
            JsonNode requestInfoNode = jsonNode.get("RequestInfo");

            if (requestInfoNode == null || requestInfoNode.get("authToken") == null) {
                chain.doFilter(request, response);
                return;
            }

            String authToken = requestInfoNode.get("authToken").asText();
            String email = requestInfoNode.get("email").asText();
            List<String> roles = objectMapper.convertValue(requestInfoNode.get("roles"), List.class);

            if (jwtService.validateToken(authToken, email)) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .toList();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
            return;
        }

        chain.doFilter(request, response);
    }
}

