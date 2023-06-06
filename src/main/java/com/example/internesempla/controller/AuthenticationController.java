package com.example.internesempla.controller;

import com.example.internesempla.dto.AuthenticationRequest;
import com.example.internesempla.dto.AuthenticationResponse;
import com.example.internesempla.dto.RegistrationRequest;
import com.example.internesempla.entity.UserEntity;
import com.example.internesempla.jwt.JWTService;
import com.example.internesempla.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final JWTService jwtService;

    public AuthenticationController(UserService userService, JWTService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest authenticationRequest,
                                                                 HttpServletResponse httpResponse) {
        AuthenticationResponse response = userService.authenticate(authenticationRequest);
        String token = jwtService.generateToken(new UserEntity(authenticationRequest.login(), authenticationRequest.password()));
        Cookie cookie = new Cookie("token", token);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        httpResponse.addCookie(cookie);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/registration")
    public AuthenticationResponse registration(@RequestBody RegistrationRequest registrationRequest) {
        return userService.register(registrationRequest);
    }

//    @GetMapping("/activate/{code}")
//    public ResponseEntity<UserEntity> activate(@PathVariable String code) {
//        return new ResponseEntity<>(userService.activateUser(code), HttpStatus.CREATED);
//    }
}