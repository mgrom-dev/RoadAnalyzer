package ru.mgrom.roadanalyzer.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * fuel consumption information
 * auto generated table by triggers
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "fuel")
public class Fuel extends BaseEntity {
    
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
