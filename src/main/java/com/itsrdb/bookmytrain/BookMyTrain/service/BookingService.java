package com.itsrdb.bookmytrain.BookMyTrain.service;

import com.itsrdb.bookmytrain.BookMyTrain.dto.StationToStationRequest;
import com.itsrdb.bookmytrain.BookMyTrain.dto.TrainResponse;
import com.itsrdb.bookmytrain.BookMyTrain.model.Schedule;
import com.itsrdb.bookmytrain.BookMyTrain.repository.BookingRepository;
import com.itsrdb.bookmytrain.BookMyTrain.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.itsrdb.bookmytrain.BookMyTrain.model.Train;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final TrainRepository trainRepository;
    private final BookingRepository bookingRepository;

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
                            .build();
                    availableTrains.add(trainResponse);
                }
            }
        }

        return availableTrains;
    }

    public long getTotalAvailableSeats(Train train, LocalDate bookingDate) {
        Long seatsTaken = bookingRepository.countBookingsByDateAndTrain(train, bookingDate);
        return train.getNoOfSeats() - seatsTaken;
    }

}
