package com.ushan.authDemo.controllers;

import com.ushan.authDemo.domains.responses.AuthenticationRequest;
import com.ushan.authDemo.domains.responses.AuthenticationResponse;
import com.ushan.authDemo.domains.responses.RegistrationRequest;
import com.ushan.authDemo.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegistrationRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) throws Exception{
        return ResponseEntity.ok(authenticationService.authenticate(request));

    }

}
