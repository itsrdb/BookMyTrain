package com.itsrdb.bookmytrain.BookMyTrain.dto;

import com.itsrdb.bookmytrain.BookMyTrain.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainAdditionRequest {
    private Long id;
    private String name;
    private List<Schedule> schedules;
    private Long noOfSeats;
}
