package com.itsrdb.bookmytrain.BookMyTrain.service;

import com.itsrdb.bookmytrain.BookMyTrain.dto.BookSeatRequest;
import com.itsrdb.bookmytrain.BookMyTrain.dto.StationToStationRequest;
import com.itsrdb.bookmytrain.BookMyTrain.dto.TrainResponse;
import com.itsrdb.bookmytrain.BookMyTrain.model.Booking;
import com.itsrdb.bookmytrain.BookMyTrain.model.Schedule;
import com.itsrdb.bookmytrain.BookMyTrain.model.User;
import com.itsrdb.bookmytrain.BookMyTrain.repository.BookingRepository;
import com.itsrdb.bookmytrain.BookMyTrain.repository.TrainRepository;
import com.itsrdb.bookmytrain.BookMyTrain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.itsrdb.bookmytrain.BookMyTrain.model.Train;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final TrainRepository trainRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    private final Semaphore semaphore = new Semaphore(1);

    public List<TrainResponse> getAvailableTrainsForSourceDestination(
            StationToStationRequest stationToStationRequest) {
        List<Train> trains = trainRepository.findAll();
        String source = stationToStationRequest.getSource();
        String destination = stationToStationRequest.getDestination();
        LocalDate bookingDate = stationToStationRequest.getDate();

        List<TrainResponse> availableTrains = new ArrayList<>();
        for (Train train: trains) {
            long availableSeats = getTotalAvailableSeats(train, bookingDate);
            if (availableSeats == 0)
                continue;

            boolean sourceFound = false;
            for (Schedule schedule: train.getSchedules()) {
                if (schedule.getStationName().equals(source)) {
                    sourceFound = true;
                }
                if (sourceFound && schedule.getStationName().equals(destination)) {
                    TrainResponse trainResponse = TrainResponse.builder()
                            .id(train.getId())
                            .name(train.getName())
                            .source(source)
                            .destination(destination)
                            .availableSeats(availableSeats)
                            .build();
                    availableTrains.add(trainResponse);
                }
            }
        }

        return availableTrains;
    }

    public void bookSeat(BookSeatRequest bookSeatRequest, String username) {
        try {
            semaphore.acquire();
            String source = bookSeatRequest.getSource();
            String destination = bookSeatRequest.getDestination();
            LocalDate bookingDate = bookSeatRequest.getDate();
            Train train = trainRepository.getReferenceById(bookSeatRequest.getTrain_id());
            Optional<User> user = userRepository.findByUsername(username);

            if (user.isEmpty()) {
                throw new Exception("Invalid user details.");
            }

            long currentSeatNumber = train.getNoOfSeats() - getTotalAvailableSeats(train, bookingDate) + 1;
            Booking currentBooking = Booking.builder()
                    .train(train)
                    .source(source)
                    .destination(destination)
                    .bookingDate(bookingDate)
                    .seatNumber(currentSeatNumber)
                    .user(user.get())
                    .build();

            bookingRepository.save(currentBooking);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }
    }

    public long getTotalAvailableSeats(Train train, LocalDate bookingDate) {
        Long seatsTaken = bookingRepository.countBookingsByDateAndTrain(train, bookingDate);
        return train.getNoOfSeats() - seatsTaken;
    }

}
