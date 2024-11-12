package ru.mgrom.roadanalyzer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * description of expenses [nomenclature list]
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "part_and_service")
@AllArgsConstructor
@NoArgsConstructor
public class PartAndService extends BaseEntity {
    
    @Column(name = "description", nullable = false)
    private String description;

    private Long type; // table expense_type
}
