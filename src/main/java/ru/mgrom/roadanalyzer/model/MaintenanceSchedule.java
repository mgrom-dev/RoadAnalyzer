package ru.mgrom.roadanalyzer.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * for storing routine maintenance data
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "maintenance_schedule")
public class MaintenanceSchedule extends BaseEntity {
    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @ElementCollection
    private List<String> requiredMaterials;

    @Column(nullable = false)
    private Integer mileageInterval;

    @Column(nullable = false)
    private Integer timeInterval;

    @Column
    private LocalDate lastExecutionDate;

    @Column
    private Integer lastOdometerReading;

    @Column(nullable = false)
    private Double cost;

    @Column
    private String notes;
}