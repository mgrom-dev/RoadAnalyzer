package ru.mgrom.roadanalyzer.service;

import java.util.List;
import java.util.Optional;

public interface ServiceInterface<T> {
    Optional<T> get(T entity, String databaseIdentifier);
    
    Optional<T> getById(Long id, String databaseIdentifier);
        
    List<T> getAll(String databaseIdentifier);
    
    boolean create(T entity, String databaseIdentifier);
    
    void delete(T entity, String databaseIdentifier);
    
    void delete(Long id, String databaseIdentifier);
    
    boolean update(T entity, String databaseIdentifier);
}
