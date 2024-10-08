package ru.mgrom.roadanalyzer.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class DatabaseSchemaService {

    private final String fileUserSchemaSQL = "db/migration/V3__Prototype_user_schema.sql";
    private final EntityManager entityManager;

    public DatabaseSchemaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void createSchemaIfNotExists(String schemaName) {
        String sqlScript = getSqlScript();
        sqlScript = sqlScript.replaceAll("\\$\\{proto_user_db\\}", schemaName);
        entityManager.createNativeQuery(sqlScript).executeUpdate();
    }

    private String getSqlScript() {
        String sqlScript = "";

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileUserSchemaSQL);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileUserSchemaSQL);
            }

            // read file
            StringBuilder sqlScriptBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlScriptBuilder.append(line).append("\n");
            }

            sqlScript = sqlScriptBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sqlScript;
    }
}