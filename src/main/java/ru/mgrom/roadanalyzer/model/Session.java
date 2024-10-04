package ru.mgrom.roadanalyzer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sessions")
public class Session extends BaseEntity {

    @Column(name = "session_id", unique = true, nullable = false)
    private String sessionId; // id session

    @Column(name = "user_id", nullable = false)
    private Long userId; // id use

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // date create session

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt; // date last visit

    @Column(name = "is_active", nullable = false)
    private boolean isActive; // status active session

    public Session() {
        this.userId = 0L;
        this.createdAt = LocalDateTime.now();
        this.isActive = true; // default session is active
        this.lastAccessedAt = LocalDateTime.now(); // set date create current time
    }
}