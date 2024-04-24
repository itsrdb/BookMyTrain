package com.itsrdb.bookmytrain.BookMyTrain.service;

import com.itsrdb.bookmytrain.BookMyTrain.dto.UserRequest;
import com.itsrdb.bookmytrain.BookMyTrain.model.User;
import com.itsrdb.bookmytrain.BookMyTrain.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void register(UserRequest userRequest, User.Role role) throws Exception {
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .role(role)
                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User login(String username, String password) throws Exception {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty() || !passwordEncoder.matches(password, userOptional.get().getPassword())) {
            throw new Exception("Invalid username or password");
        }
        return userOptional.get();
    }
}