package ru.mgrom.roadanalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mgrom.roadanalyzer.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Session findBySessionId(String sessionId);
}