package ru.mgrom.roadanalyzer.dto;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class SpendingDTO {
    @Id
    private Long id;
    private LocalDate date;
    private Long partAndServiceId;
    private String description;
    private Double count;
    private Double amount;
    private String partDescription;
    private Long partType;
    private String partTypeDescription;
}
