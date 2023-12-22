package com.ushan.authDemo.domains.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
    private String userName;
    private String password;
    private LocalDate dob;
    private String email;
}
