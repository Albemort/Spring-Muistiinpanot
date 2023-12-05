package com.example.Muistiinpanot;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "event_categories")
@Data
@EqualsAndHashCode(callSuper = true)
public class EventCategory extends AbstractPersistable<Long> {

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}