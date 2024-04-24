package com.itsrdb.bookmytrain.BookMyTrain.repository;

import com.itsrdb.bookmytrain.BookMyTrain.model.Train;
import com.itsrdb.bookmytrain.BookMyTrain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainRepository  extends JpaRepository<Train, Long> {
//    Optional<Train> findById(Long id);
}
