package com.itsrdb.bookmytrain.BookMyTrain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "t_trains")
public class Train {

    @Id
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "schedule_map", joinColumns = @JoinColumn(name = "entity_id"))
    private List<Schedule> schedules;

    @Column(nullable = false)
    private Long noOfSeats;

    @Column(nullable = false, columnDefinition = "bigint default 0")
    private Long noOfSeatsBooked;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    private Long getNoOfSeatsAvailable() {
        return noOfSeats - noOfSeatsBooked ;
    }

}
