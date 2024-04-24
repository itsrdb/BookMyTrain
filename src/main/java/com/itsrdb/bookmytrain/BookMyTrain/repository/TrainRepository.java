package com.itsrdb.bookmytrain.BookMyTrain.repository;

import com.itsrdb.bookmytrain.BookMyTrain.model.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository  extends JpaRepository<Train, Long> {
}
