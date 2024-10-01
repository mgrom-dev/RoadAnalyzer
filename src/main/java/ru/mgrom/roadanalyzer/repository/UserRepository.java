package ru.mgrom.roadanalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.mgrom.roadanalyzer.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByDatabaseIdentifier(String databaseIdentifier);
}