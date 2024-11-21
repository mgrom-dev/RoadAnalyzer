package ru.mgrom.roadanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdResponse {
    private String message;
    private Long id;
}
