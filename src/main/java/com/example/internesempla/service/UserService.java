package com.example.internesempla.service;

import com.example.internesempla.dto.AuthenticationRequest;
import com.example.internesempla.dto.AuthenticationResponse;
import com.example.internesempla.dto.RegistrationRequest;
import com.example.internesempla.entity.UserEntity;
import com.example.internesempla.enumeration.RoleEnum;
import com.example.internesempla.jwt.JWTService;
import com.example.internesempla.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTService jwtService, AuthenticationManager authenticationManager, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.mailService = mailService;
    }
//TODO : registration
    public AuthenticationResponse register(RegistrationRequest registrationRequest){
        var userEntity = new UserEntity(0,
                registrationRequest.login(),
                registrationRequest.email(),
                registrationRequest.firstName(),
                registrationRequest.lastName(),
                passwordEncoder.encode(registrationRequest.password()),
                RoleEnum.USER,
                UUID.randomUUID().toString(),
                false
        );

        var loginAlreadyExist = userRepository.findByLogin(userEntity.getLogin());
        var emailAlreadyExist = userRepository.findByEmail(userEntity.getEmail());

        if(loginAlreadyExist.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists!");
        }

        if(emailAlreadyExist.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }
        var token = jwtService.generateToken(userEntity);
//        var message = String.format(
//                "press on link to activate your account http://localhost:9090/api/auth/activate/%s", userEntity.getActivationCode());
//        mailService.sendMail(userEntity.getEmail(), "Verification email", message);
        userRepository.save(userEntity);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.login(),
                        authenticationRequest.password()
                )
        );

        var userEntity = this.userRepository.findByLogin(authenticationRequest.login()).orElseThrow();

        return new AuthenticationResponse(jwtService.generateToken(userEntity));
    }

//    public UserEntity activateUser(String code) {
//        UserEntity user = userRepository.findByActivationCode(code).orElseThrow(() -> new IllegalStateException("Not activated"));
//        user.setIsActivated(true);
//        return userRepository.save(user);
//    }
}