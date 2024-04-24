package com.itsrdb.bookmytrain.BookMyTrain.service;

import com.itsrdb.bookmytrain.BookMyTrain.dto.StationToStationRequest;
import com.itsrdb.bookmytrain.BookMyTrain.dto.TrainResponse;
import com.itsrdb.bookmytrain.BookMyTrain.model.Schedule;
import com.itsrdb.bookmytrain.BookMyTrain.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.itsrdb.bookmytrain.BookMyTrain.model.Train;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final TrainRepository trainRepository;

    public List<TrainResponse> getAvailableTrainsForSourceDestination(
            StationToStationRequest stationToStationRequest) {
        List<Train> trains = trainRepository.findAll();
        String source = stationToStationRequest.getSource();
        String destination = stationToStationRequest.getDestination();

        List<TrainResponse> availableTrains = new ArrayList<>();
        for (Train train: trains) {
            // Need to find available seats per train

            Boolean sourceFound = false;
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

}
