package ru.mgrom.roadanalyzer.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * users web app
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password; // saved hashed password

    @Column(name = "role", nullable = false)
    private String role;

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
