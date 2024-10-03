package ru.mgrom.roadanalyzer.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * detailed expenses by date
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Spending extends BaseEntity {

    private LocalDate date;

    private Long partAndServiceId; // table part_and_service

    private String description;

    private Double count;

    private Double amount;
}
