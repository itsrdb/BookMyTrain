package com.itsrdb.bookmytrain.BookMyTrain.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

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
    @CollectionTable(name = "t_schedules", joinColumns = @JoinColumn(name = "train_id"))
    private List<Schedule> schedules;

    @Column(nullable = false)
    private Long noOfSeats;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

}
