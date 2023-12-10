package com.example.Muistiinpanot;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends AbstractPersistable<Long> {
    @NotEmpty
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private List<Event> events;

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
}