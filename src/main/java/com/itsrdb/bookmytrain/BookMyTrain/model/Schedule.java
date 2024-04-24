package com.itsrdb.bookmytrain.BookMyTrain.model;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Data
@Builder
@Embeddable
public class Schedule {

    private String stationName;
    private LocalTime arrivalTime;

    public Schedule() {
    }

    public Schedule(String stationName, LocalTime arrivalTime) {
        this.stationName = stationName;
        this.arrivalTime = arrivalTime;
    }
}
