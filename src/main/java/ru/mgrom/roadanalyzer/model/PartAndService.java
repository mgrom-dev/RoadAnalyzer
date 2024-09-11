package ru.mgrom.roadanalyzer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description of expenses [nomenclature list]
 */
@Data
@Entity
@Table(name = "part_and_service")
public class PartAndService {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    Long id;

    @Column(name = "description", nullable = false)
    private String description;

    private Long type; // table expense_type
}
