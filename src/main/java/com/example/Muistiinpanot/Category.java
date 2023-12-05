package com.example.Muistiinpanot;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = true)
public class Category extends AbstractPersistable<Long> {
    @Column(name = "category_name", nullable = false)
    private String categoryName;
}