package com.itsrdb.bookmytrain.BookMyTrain.service;

import com.itsrdb.bookmytrain.BookMyTrain.dto.TrainAdditionRequest;
import com.itsrdb.bookmytrain.BookMyTrain.model.Train;
import com.itsrdb.bookmytrain.BookMyTrain.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final TrainRepository trainRepository;

    public Train addTrain(TrainAdditionRequest trainAddRequest){
        Train train = Train.builder()
                .id(trainAddRequest.getId())
                .name(trainAddRequest.getName())
                .noOfSeats(trainAddRequest.getNoOfSeats())
                .schedules(trainAddRequest.getSchedules())
                .build();

        trainRepository.save(train);
        return train;
    }

}
