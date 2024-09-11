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
 * fuel consumption information
 * auto generated table by triggers
 */
@Data
@Entity
@Table(name = "fuel")
public class Fuel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    Long id;

    @Column(name = "spending_id")
    private Long spendingId;

    private LocalDate date;

    @Column(name = "fuel_description")
    private String fuelDescription;

    private Double count;

    private Double price;

    private Double amount;

    private Integer odometer;

    private String description;
}
