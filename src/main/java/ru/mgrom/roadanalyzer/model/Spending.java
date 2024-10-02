package ru.mgrom.roadanalyzer.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * detailed expenses by date
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Spending {
    @Id
    Long id;

    private LocalDate date;

    private Long partAndServiceId; // table part_and_service

    private String description;

    private Double count;

    private Double amount;
}
