package com.itsrdb.bookmytrain.BookMyTrain.controller;

import com.itsrdb.bookmytrain.BookMyTrain.dto.UserRequest;
import com.itsrdb.bookmytrain.BookMyTrain.model.User;
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

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) throws Exception {
        try {
            userService.register(userRequest, User.Role.USER);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody UserRequest userRequest) throws Exception {
        try {
            userService.register(userRequest, User.Role.ADMIN);
            return new ResponseEntity<>("Admin resgistered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}