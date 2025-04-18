package ru.mgrom.roadanalyzer.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
    Long save(T entity, String databaseIdentifier);

    boolean update(T entity, String databaseIdentifier);

    Optional<T> find(T entity, String databaseIdentifier);

    Optional<T> findById(Long id, String databaseIdentifier);

    List<T> findAll(String databaseIdentifier);

    void deleteById(Long id, String databaseIdentifier);

    void delete(T entity, String databaseIdentifier);
}