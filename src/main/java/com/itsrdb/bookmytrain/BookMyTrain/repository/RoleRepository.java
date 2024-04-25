package com.itsrdb.bookmytrain.BookMyTrain.repository;

import com.itsrdb.bookmytrain.BookMyTrain.entites.Role;
import com.itsrdb.bookmytrain.BookMyTrain.entites.RoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(RoleEnum name);
}
