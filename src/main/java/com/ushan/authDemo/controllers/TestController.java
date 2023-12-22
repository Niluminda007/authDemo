package com.ushan.authDemo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping(path = "/hello")
    public ResponseEntity<String> sayHello(){
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }
}
