package com.example.Enterprise.Resource.Suite.ERS.Config;

import com.example.Enterprise.Resource.Suite.ERS.Services.Impl.JwtService;
import com.example.Enterprise.Resource.Suite.ERS.Services.Impl.MyUserDetailsService;
import com.example.Enterprise.Resource.Suite.ERS.Services.Impl.RoleAccessServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class jwtAuthFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private final MyUserDetailsService userDetailsService;
    private final JwtService jwtService;

    private final RoleAccessServiceImpl roleAccessService;

    @Autowired
    public jwtAuthFilter(ObjectMapper objectMapper, MyUserDetailsService userDetailsService, JwtService jwtService, RoleAccessServiceImpl roleAccessService) {
        this.objectMapper = objectMapper;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.roleAccessService = roleAccessService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        String username = null;
        List<String> roles = null;
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
            roles = jwtService.extractRoles(token);
        }


        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                roles = jwtService.extractRoles(token);
                boolean hasValidRole = true;

                if (roles != null) {
                    String endPoints = request.getRequestURI();
                    List<String> requiredRoles = roleAccessService.getRolesForEndPoint(endPoints);

                    hasValidRole = requiredRoles.stream().anyMatch(roles::contains);

                    if (!hasValidRole) {
                        log.error("Trying to access wrong {} to {}", endPoints, username);
                        sendUnauthorizedError(response, "You don't have permission to access this API");
                        return;
                    }
                }
            }
            else{
                sendUnauthorizedError(response, "Invalid JWT Token");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void sendUnauthorizedError(HttpServletResponse response, String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        errorBody.put("error", "Unauthorized");
        errorBody.put("message", message);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorBody));
    }
}