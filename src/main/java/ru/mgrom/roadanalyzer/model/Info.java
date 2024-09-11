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
 * vehicle information, e.g.: {id=1, name="vehicle date", value="14.09.2013"}
 */
@Data
@Entity
@Table(name = "info")
public class Info {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    Long id;

    @Column(name = "key_info", nullable = false)
    private String keyInfo;

    @Column(name = "value_info", nullable = false)
    private String valueInfo;
}
