package com.ushan.authDemo.config;


import com.ushan.authDemo.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  UserDetailsService userDetailsService;

    private final HandlerExceptionResolver exceptionResolver;
    @Autowired
    public JwtAuthenticationFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {

        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws InvalidBearerTokenException, ServletException, IOException {
        try{
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userName;
            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            jwt = authHeader.substring(7);
            userName = jwtService.extractUsername(jwt);

            if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
                if(jwtService.isTokenValid(jwt, userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);

        }catch (Exception ex) {
            exceptionResolver.resolveException(request,response,null,ex);
        }
    }
}
