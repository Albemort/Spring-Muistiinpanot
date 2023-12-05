package com.example.Muistiinpanot;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
@Data
@EqualsAndHashCode(callSuper = true)
public class Event extends AbstractPersistable<Long> {

    @Column(name = "event_title", nullable = false)
    private String eventTitle;

    @Column(name = "event_description", columnDefinition = "TEXT")
    private String eventDescription;

    @Column(name = "event_date")
    private String eventDate;

}