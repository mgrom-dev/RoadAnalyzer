package ru.mgrom.roadanalyzer.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * spare parts inventory
 * auto generated table by triggers
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "parts_stock")
public class PartsStock extends BaseEntity {

    @Column(name = "spending_id")
    private Long spendingId;

    private LocalDate date;

    @Column(name = "part_description")
    private String partDescription;

    private String description;

    private String OEM;

    private Double count;

    private Double price;

    private Double amount;

    private String status;

    @Column(name = "part_group_id")
    private Long partGroupId;
}
