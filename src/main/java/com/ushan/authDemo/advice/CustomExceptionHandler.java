package com.ushan.authDemo.advice;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;


@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    ProblemDetail handleAuthenticationException(Exception ex){
        return createProblemDetail(HttpStatus.UNAUTHORIZED, ex.getMessage(), "Authentication Failure");
    }
    @ExceptionHandler(InsufficientAuthenticationException .class)
    ProblemDetail handleInsufficientAuthenticationException(Exception ex){
        return createProblemDetail(HttpStatus.UNAUTHORIZED, ex.getMessage(), "Login credentials are missing.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    ProblemDetail handleInvalidBearerTokenException(Exception ex){
        return createProblemDetail(HttpStatus.UNAUTHORIZED, ex.getMessage(), "JWT Signature not valid!!!!");
    }

    @ExceptionHandler(InvalidBearerTokenException.class)
    ProblemDetail handleUnAuthorizedException(Exception ex){
        return createProblemDetail(HttpStatus.FORBIDDEN, ex.getMessage(), "You are not Authorized to access the content!!!");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    ProblemDetail handleAccessDeniedException(NoHandlerFoundException ex) {
        return createProblemDetail(HttpStatus.NOT_FOUND, ex.getMessage(), "The endpoint you are looking for doesn't exist");
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleOtherException(Exception ex) {
        log.error("An unexpected error occurred", ex);
        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "A server internal error occurs.");
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String detail, String accessDeniedReason) {
        ProblemDetail error = ProblemDetail.forStatusAndDetail(status, detail);
        error.setProperty("access_denied_reason", accessDeniedReason);
        return error;
    }
}
