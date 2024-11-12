package ru.mgrom.roadanalyzer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import ru.mgrom.roadanalyzer.repository.GenericRepository;

public abstract class BaseService<M, R extends GenericRepository<M>> {
    @Autowired
    protected R repository;

    public Optional<M> get(M entity, String databaseIdentifier) {
        return repository.find(entity, databaseIdentifier);
    }

    public Optional<M> getById(Long id, String databaseIdentifier) {
        return repository.findById(id, databaseIdentifier);
    }

    public List<M> getAll(String databaseIdentifier) {
        return repository.findAll(databaseIdentifier);
    }

    public boolean create(M entity, String databaseIdentifier) {
        return repository.save(entity, databaseIdentifier);
    }

    public void delete(M entity, String databaseIdentifier) {
        repository.delete(entity, databaseIdentifier);
    }

    public void delete(Long id, String databaseIdentifier) {
        repository.deleteById(id, databaseIdentifier);
    }

    public boolean update(M entity, String databaseIdentifier) {
        return repository.update(entity, databaseIdentifier);
    }
}
