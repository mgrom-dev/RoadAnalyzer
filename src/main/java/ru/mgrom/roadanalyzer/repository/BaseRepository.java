package ru.mgrom.roadanalyzer.repository;

import java.lang.reflect.Field;
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

    protected final Class<T> entityClass;

    protected final String tableName;

    public BaseRepository(Class<T> entityClass, String tableName) {
        this.entityClass = entityClass;
        this.tableName = tableName;
    }

    private static String camelToSnake(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.replaceAll("([a-z])([A-Z])", "$1_$2").toLowerCase();
    }

    protected Query createQuery(String sql) {
        return entityManager.createNativeQuery(sql);
    }

    protected Query createQuery(String sql, Class<?> resultClass) {
        return entityManager.createNativeQuery(sql, resultClass);
    }

    @Transactional
    public boolean save(T entity, String databaseIdentifier) {
        boolean isSaved = false;
        try {
            Field[] fields = entity.getClass().getDeclaredFields();

            // sql query generation
            StringBuilder sql = new StringBuilder("INSERT INTO " + databaseIdentifier + "." + tableName + " (");
            StringBuilder valuesPlaceholder = new StringBuilder(" VALUES (");

            for (Field field : fields) {
                field.setAccessible(true); // making the field readable
                if (field.getName().equals("id")) {
                    continue; // skip the id field
                }
                String columnName = camelToSnake(field.getName()); // convert the field name to snake_case
                sql.append(columnName).append(", ");
                valuesPlaceholder.append(":").append(field.getName()).append(", ");
            }

            // remove the last commas and add closing brackets
            sql.setLength(sql.length() - 2); // remove last comma and space
            valuesPlaceholder.setLength(valuesPlaceholder.length() - 2);
            sql.append(")").append(valuesPlaceholder).append(")");

            Query query = createQuery(sql.toString());
            for (Field field : fields) {
                if (field.getName().equals("id")) {
                    continue;
                }
                query.setParameter(field.getName(), field.get(entity));
            }

            query.executeUpdate();
            isSaved = true;
        } catch (Exception e) {
            System.out.println("error saving entity: " + e.getMessage());
        }
        return isSaved;
    }

    @SuppressWarnings("unchecked")
    public Optional<T> find(T entity, String databaseIdentifier) {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + databaseIdentifier + "." + tableName + " WHERE ");
        StringBuilder whereClause = new StringBuilder();

        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("id")) {
                continue;
            }
            try {
                Object value = field.get(entity);
                if (value != null) {
                    String columnName = camelToSnake(field.getName());
                    whereClause.append(columnName).append(" = :").append(field.getName()).append(" AND ");
                }
            } catch (IllegalAccessException e) {
                System.out.println("Error accessing field: " + e.getMessage());
            }
        }

        // remove last AND
        if (whereClause.length() > 0) {
            whereClause.setLength(whereClause.length() - 5);
        }
        sql.append(whereClause);

        Query query = createQuery(sql.toString(), entity.getClass());
        // set params for query
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.getName().equals("id")) {
                continue;
            }
            try {
                Object value = field.get(entity);
                if (value != null) {
                    query.setParameter(field.getName(), value);
                }
            } catch (IllegalAccessException e) {
                System.out.println("Error accessing field: " + e.getMessage());
            }
        }

        return Optional.ofNullable((T) query.getSingleResult());
    }

    @SuppressWarnings("unchecked")
    public Optional<T> findById(Long id, String databaseIdentifier) {
        String sql = "SELECT * FROM " + databaseIdentifier + "." + tableName + " WHERE id = :id LIMIT 1";

        Query query = createQuery(sql, entityClass);
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
    public List<T> findAll(String databaseIdentifier) {
        String sql = "SELECT * FROM " + databaseIdentifier + "." + tableName;
        Query query = createQuery(sql, entityClass);

        List<T> entities = new ArrayList<>();
        for (Object obj : query.getResultList()) {
            entities.add((T) obj);
        }

        return entities;
    }

    @Transactional
    public void deleteById(Long id, String databaseIdentifier) {
        String sql = "DELETE FROM " + databaseIdentifier + "." + tableName + " WHERE id = :id";
        Query query = createQuery(sql);
        query.setParameter("id", id);

        query.executeUpdate();
    }

    @Transactional
    public void delete(T entity, String databaseIdentifier) {
        deleteById(((BaseEntity) entity).getId(), databaseIdentifier);
    }
}