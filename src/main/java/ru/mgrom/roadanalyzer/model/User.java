package ru.mgrom.roadanalyzer.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * users web app
 */
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password; // saved hashed password

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "database_identifier", unique = true, nullable = false)
    private String databaseIdentifier; // unique id database

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive; // user status

    public User() {
        this.createdAt = LocalDateTime.now();
        this.isActive = true; // default user is active
    }
}
