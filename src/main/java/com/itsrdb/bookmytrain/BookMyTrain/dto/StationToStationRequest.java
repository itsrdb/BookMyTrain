package com.itsrdb.bookmytrain.BookMyTrain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StationToStationRequest {
    private String source;
    private String destination;
    private LocalDate date;
}
