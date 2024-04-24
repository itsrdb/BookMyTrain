package com.itsrdb.bookmytrain.BookMyTrain.controller;

import com.itsrdb.bookmytrain.BookMyTrain.dto.LoginResponse;
import com.itsrdb.bookmytrain.BookMyTrain.dto.UserRequest;
import com.itsrdb.bookmytrain.BookMyTrain.model.User;
import com.itsrdb.bookmytrain.BookMyTrain.service.JwtService;
import com.itsrdb.bookmytrain.BookMyTrain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingServiceController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {
        try {
            userService.register(userRequest, User.Role.USER);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to register user", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody UserRequest userRequest) {
        try {
            userService.register(userRequest, User.Role.ADMIN);
            return new ResponseEntity<>("Admin registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to register admin user", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody UserRequest userRequest) {
        User authenticatedUser = userService.authenticate(userRequest);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .build();

        return ResponseEntity.ok(loginResponse);
    }

}