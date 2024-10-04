package ru.mgrom.roadanalyzer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * type of expense (e.g. fuel, services, spare parts) [type expense list]
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "expense_type")
public class ExpenseType extends BaseEntity {
    
    @Column(name = "description", nullable = false)
    private String description;
}
