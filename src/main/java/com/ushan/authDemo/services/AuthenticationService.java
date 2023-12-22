package com.ushan.authDemo.services;

import com.ushan.authDemo.domains.responses.AuthenticationRequest;
import com.ushan.authDemo.domains.responses.AuthenticationResponse;
import com.ushan.authDemo.domains.responses.RegistrationRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    AuthenticationResponse register(RegistrationRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception;
}
