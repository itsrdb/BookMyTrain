package com.itsrdb.bookmytrain.BookMyTrain.controller;

import com.itsrdb.bookmytrain.BookMyTrain.dto.*;
import com.itsrdb.bookmytrain.BookMyTrain.entites.RoleEnum;
import com.itsrdb.bookmytrain.BookMyTrain.model.Train;
import com.itsrdb.bookmytrain.BookMyTrain.model.User;
import com.itsrdb.bookmytrain.BookMyTrain.service.AdminService;
import com.itsrdb.bookmytrain.BookMyTrain.service.BookingService;
import com.itsrdb.bookmytrain.BookMyTrain.service.JwtService;
import com.itsrdb.bookmytrain.BookMyTrain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BookingServiceController {

    private final UserService userService;
    private final AdminService adminService;
    private final BookingService bookingService;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {
        try {
            userService.register(userRequest, RoleEnum.USER);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Unable to register user", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/registerAdmin")
    public ResponseEntity<String> registerAdmin(@RequestBody UserRequest userRequest) {
        try {
            userService.register(userRequest, RoleEnum.ADMIN);
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

    @PostMapping("/addTrain")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Train> addTrain(@RequestBody TrainAdditionRequest trainAddRequest) {
        try {
            Train newTrain = adminService.addTrain(trainAddRequest);
            return ResponseEntity.ok(newTrain);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTrains")
    public ResponseEntity<List<TrainResponse>> getTrains(@RequestBody StationToStationRequest stationToStationRequest) {
        try {
            List<TrainResponse> availableTrains = bookingService
                    .getAvailableTrainsForSourceDestination(stationToStationRequest);
            return ResponseEntity.ok(availableTrains);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/bookSeat")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> bookSeat(@RequestBody BookSeatRequest bookSeatRequest) {
        try {
            Authentication authToken = SecurityContextHolder.getContext().getAuthentication();
            String username = authToken.getName();

            bookingService.bookSeat(bookSeatRequest, username);
            return new ResponseEntity<>("Booking successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Sorry, could not book seat", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAllBookings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BookingResponse>> getBookingResponse() {
        try {
            Authentication authToken = SecurityContextHolder.getContext().getAuthentication();
            String username = authToken.getName();

            return ResponseEntity.ok(bookingService.getAllBookings(username));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getBookingsOnDate")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<BookingResponse>> getBookingsOnDate(@RequestBody
                                                                       BookingsOnDateRequest bookingsOnDateRequest) {
        try {
            Authentication authToken = SecurityContextHolder.getContext().getAuthentication();
            String username = authToken.getName();
            LocalDate bookingDate = bookingsOnDateRequest.getDate();

            return ResponseEntity.ok(bookingService.getBookingsOnDate(username, bookingDate));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}