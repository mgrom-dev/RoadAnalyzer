package ru.mgrom.roadanalyzer.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class DatabaseSchemaService {

    private final EntityManager entityManager;

    public DatabaseSchemaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void createSchemaIfNotExists(String schemaName) {
        String createSchemaSql = "CREATE SCHEMA IF NOT EXISTS " + schemaName;
        entityManager.createNativeQuery(createSchemaSql).executeUpdate();

        createRequiredTables(schemaName);
    }

    private void createRequiredTables(String schemaName) {
        createExpenseTypeTable(schemaName);
        createFuelTable(schemaName);
        createInfoTable(schemaName);
        createPartAndServiceTable(schemaName);
        createPartGroupTable(schemaName);
        createPartsStockTable(schemaName);
        createSpendingTable(schemaName);
    }

    private void createExpenseTypeTable(String schemaName) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + schemaName + ".expense_type (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "description VARCHAR(255) NOT NULL" +
                ")";

        entityManager.createNativeQuery(createTableSql).executeUpdate();
    }

    private void createFuelTable(String schemaName) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + schemaName + ".fuel (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "spending_id BIGINT, " +
                "date DATE, " +
                "fuel_description VARCHAR(255), " +
                "count DOUBLE, " +
                "price DOUBLE, " +
                "amount DOUBLE, " +
                "odometer INTEGER, " +
                "description VARCHAR(255)" +
                ")";

        entityManager.createNativeQuery(createTableSql).executeUpdate();
    }

    private void createInfoTable(String schemaName) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + schemaName + ".info (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "key_info VARCHAR(255) NOT NULL, " +
                "value_info VARCHAR(255) NOT NULL" +
                ")";

        entityManager.createNativeQuery(createTableSql).executeUpdate();
    }

    private void createPartAndServiceTable(String schemaName) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + schemaName + ".part_and_service (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "description VARCHAR(255) NOT NULL, " +
                "type BIGINT" + // links to table expense_type
                ")";

        entityManager.createNativeQuery(createTableSql).executeUpdate();
    }

    private void createPartGroupTable(String schemaName) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + schemaName + ".part_group (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "description VARCHAR(255) NOT NULL" +
                ")";

        entityManager.createNativeQuery(createTableSql).executeUpdate();
    }

    private void createPartsStockTable(String schemaName) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + schemaName + ".parts_stock (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "spending_id BIGINT, " +
                "date DATE, " +
                "part_description VARCHAR(255), " +
                "description VARCHAR(255), " +
                "OEM VARCHAR(255), " +
                "count DOUBLE, " +
                "price DOUBLE, " +
                "amount DOUBLE, " +
                "status VARCHAR(255), " +
                "part_group_id BIGINT" +
                ")";

        entityManager.createNativeQuery(createTableSql).executeUpdate();
    }

    private void createSpendingTable(String schemaName) {
        String createTableSql = "CREATE TABLE IF NOT EXISTS " + schemaName + ".spending (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "date DATE NOT NULL, " +
                "part_and_service_id BIGINT, " +
                "description VARCHAR(255), " +
                "count DOUBLE, " +
                "amount DOUBLE" +
                ")";

        entityManager.createNativeQuery(createTableSql).executeUpdate();
    }
}