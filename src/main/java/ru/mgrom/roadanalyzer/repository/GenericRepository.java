package ru.mgrom.roadanalyzer.repository;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<T> {
    T save(T entity);

    Optional<T> findById(Long id);

    List<T> findAll();

    void deleteById(Long id);

    void delete(T entity);
}