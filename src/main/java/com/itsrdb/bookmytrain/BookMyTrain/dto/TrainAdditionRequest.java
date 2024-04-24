package com.itsrdb.bookmytrain.BookMyTrain.dto;

import com.itsrdb.bookmytrain.BookMyTrain.model.Schedule;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainAdditionRequest {

    private Long id;
    private String name;
    private List<Schedule> schedules;
    private Long noOfSeats;
    private Long noOfSeatsBooked;
    private Date createdAt;
    private Date updatedAt;

}
