package com.itsrdb.bookmytrain.BookMyTrain.repository;

import com.itsrdb.bookmytrain.BookMyTrain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}