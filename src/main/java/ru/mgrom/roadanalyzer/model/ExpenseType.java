package ru.mgrom.roadanalyzer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * type of expense (e.g. fuel, services, spare parts) [type expense list]
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "expense_type")
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseType extends BaseEntity {
    
    @Column(name = "description", nullable = false)
    private String description;
}
