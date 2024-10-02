package ru.mgrom.roadanalyzer.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import ru.mgrom.roadanalyzer.model.BaseEntity;
import ru.mgrom.roadanalyzer.service.DatabaseSchemaService;

public abstract class BaseRepository<T> {

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected DatabaseSchemaService databaseSchemaService;

    protected String getDatabaseIdentifier() {
        return databaseSchemaService.getDatabaseIdentifierFromSession();
    }

    protected Query createQuery(String sql) {
        return entityManager.createNativeQuery(sql);
    }

    protected Query createQuery(String sql, Class<?> resultClass) {
        return entityManager.createNativeQuery(sql, resultClass);
    }

    protected abstract String getTableName();

    protected abstract Class<T> getEntityClass();

    @SuppressWarnings("unchecked")
    public Optional<T> findById(Long id) {
        String sql = "SELECT * FROM " + getDatabaseIdentifier() + "." + getTableName() + " WHERE id = :id LIMIT 1";
        Query query = createQuery(sql, getEntityClass());
        query.setParameter("id", id);

        T entity = null;
        try {
            entity = (T) query.getSingleResult();
        } catch (NoResultException e) {
            // ignore exception
        }

        return Optional.ofNullable(entity);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        String sql = "SELECT * FROM " + getDatabaseIdentifier() + "." + getTableName();
        Query query = createQuery(sql, getEntityClass());

        List<T> entities = new ArrayList<>();
        for (Object obj : query.getResultList()) {
            entities.add((T) obj);
        }

        return entities;
    }

    @Transactional
    public void deleteById(Long id) {
        String sql = "DELETE FROM " + getDatabaseIdentifier() + "." + getTableName() + " WHERE id = :id";
        Query query = createQuery(sql);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    @Transactional
    public void delete(T entity) {
        deleteById(((BaseEntity) entity).getId());
    }
}