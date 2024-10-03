package ru.mgrom.roadanalyzer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * description of expenses [nomenclature list]
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "part_and_service")
public class PartAndService extends BaseEntity {
    
    @Column(name = "description", nullable = false)
    private String description;

    private Long type; // table expense_type
}
