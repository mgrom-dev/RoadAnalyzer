package ru.mgrom.roadanalyzer.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * detailed expenses by date
 */
@Data
@Entity
@Table(name = "spending")
public class Spending {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "part_and_service_id")
    private Long partAndServiceId; // table part_and_service

    private String description;

    private Double count;

    private Double amount;

    public Spending() {
        this.description = "";
    }
}
