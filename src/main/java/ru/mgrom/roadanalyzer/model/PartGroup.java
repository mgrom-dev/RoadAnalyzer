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
 * type of spare parts (e.g. brake system, tools, engine oil)
 * for grouping by machine components [type spare parts list]
 */
@Data
@Entity
@Table(name = "part_group")
public class PartGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    Long id;

    @Column(name = "description", nullable = false)
    private String description;
}
