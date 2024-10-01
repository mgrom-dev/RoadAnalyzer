package ru.mgrom.roadanalyzer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SessionResponse {
    private Long userId;
    private String sessionId;
    private String email;
    private String databaseId;
    private LocalDateTime createdAt;
    private boolean active;
}