package com.itsrdb.bookmytrain.BookMyTrain.repository;

import com.itsrdb.bookmytrain.BookMyTrain.model.Train;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;

@RepositoryRestResource
public interface BookingRepository {
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.bookingDate = :date AND b.train = :train")
    long countBookingsByDateAndTrain(@Param("train") Train train, @Param("date") LocalDate date);
}
