package com.ushan.authDemo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface JwtService {
    String extractUsername(String jwt);

    boolean isTokenValid(String jwt, UserDetails userDetails);

    String generateToken(UserDetails userDetails);
    String generateToken(Map<String, Object>  extraClaims, UserDetails userDetails);


}
