package com.itsrdb.bookmytrain.BookMyTrain.service;

import com.itsrdb.bookmytrain.BookMyTrain.dto.UserRequest;
import com.itsrdb.bookmytrain.BookMyTrain.entites.Role;
import com.itsrdb.bookmytrain.BookMyTrain.entites.RoleEnum;
import com.itsrdb.bookmytrain.BookMyTrain.model.User;
import com.itsrdb.bookmytrain.BookMyTrain.repository.RoleRepository;
import com.itsrdb.bookmytrain.BookMyTrain.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    public void register(UserRequest userRequest, RoleEnum role) {
        Role userRole = roleRepository.findByName(role).get();

        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .role(userRole)
                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User authenticate(UserRequest userRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getUsername(),
                        userRequest.getPassword()
                )
        );
        return userRepository.findByUsername(userRequest.getUsername())
                .orElseThrow();
    }
}