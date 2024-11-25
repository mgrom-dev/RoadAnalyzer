package ru.mgrom.roadanalyzer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * vehicle information, e.g.: {id=1, name="vehicle date", value="14.09.2013"}
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "info")
public class Info extends BaseEntity {
    
    @Column(name = "key_info", nullable = false)
    private String keyInfo;

    @Column(name = "value_info", nullable = false)
    private String valueInfo;
}
