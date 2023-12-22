package com.ushan.authDemo.services.Impl;

import com.ushan.authDemo.enums.UserRole;
import com.ushan.authDemo.domains.entities.UserEntity;
import com.ushan.authDemo.domains.responses.AuthenticationRequest;
import com.ushan.authDemo.domains.responses.AuthenticationResponse;
import com.ushan.authDemo.domains.responses.RegistrationRequest;
import com.ushan.authDemo.repositories.UserRepository;
import com.ushan.authDemo.services.AuthenticationService;
import com.ushan.authDemo.services.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegistrationRequest request) {
        UserEntity user = UserEntity.builder()
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .dob(request.getDob())
                .email(request.getEmail())
                .userRole(UserRole.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),
                            request.getPassword()
                    )
            );

            UserEntity user = userRepository.findByUserName(request.getUserName()).orElseThrow();
            String jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
    }
}

