package com.example.internesempla.controller;

import com.example.internesempla.dto.AuthenticationRequest;
import com.example.internesempla.dto.AuthenticationResponse;
import com.example.internesempla.dto.RegistrationRequest;
import com.example.internesempla.entity.UserEntity;
import com.example.internesempla.jwt.JWTService;
import com.example.internesempla.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final JWTService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    public AuthenticationController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/authentication")
    public AuthenticationResponse authentication(@RequestBody AuthenticationRequest authenticationRequest) {
        logger.info("user {} authenticated", authenticationRequest.login());
        return userService.authenticate(authenticationRequest);
    }

    @PostMapping("/registration")
    public AuthenticationResponse registration(@RequestBody RegistrationRequest registrationRequest) {
        logger.info("user {} registered", registrationRequest.login());
        return userService.register(registrationRequest);
    }

    @GetMapping("/activate/{code}")
    public ResponseEntity<UserEntity> activate(@PathVariable String code) {
        logger.info("the account has been activated");
        return ResponseEntity.ok(userService.activateUser(code));
    }
}