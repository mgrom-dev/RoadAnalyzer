package ru.mgrom.roadanalyzer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * type of spare parts (e.g. brake system, tools, engine oil)
 * for grouping by machine components [type spare parts list]
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "part_group")
public class PartGroup extends BaseEntity {
    
    @Column(name = "description", nullable = false)
    private String description;
}
