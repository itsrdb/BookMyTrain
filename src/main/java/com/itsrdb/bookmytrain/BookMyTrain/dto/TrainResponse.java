package com.itsrdb.bookmytrain.BookMyTrain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainResponse {
    private Long id;
    private String name;
    private String source;
    private String destination;
    private Long availableSeats;
}
