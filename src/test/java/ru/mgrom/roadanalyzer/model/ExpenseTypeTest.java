package ru.mgrom.roadanalyzer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExpenseTypeTest {

    @Test
    void testConstructorAndGetterSetter() {
        String description = "Fuel Expense";

        ExpenseType expenseType = new ExpenseType(description);

        assertEquals(description, expenseType.getDescription());
    }

    @Test
    void testNoArgsConstructor() {
        ExpenseType expenseType = new ExpenseType();

        assertNull(expenseType.getDescription());
    }

    @Test
    void testSetters() {
        ExpenseType expenseType = new ExpenseType();

        expenseType.setDescription("New Fuel Expense");

        assertEquals("New Fuel Expense", expenseType.getDescription());
    }

    @Test
    void testToString() {
        ExpenseType expenseType = new ExpenseType("Test Description");

        String toString = expenseType.toString();

        assertTrue(toString.contains("description=Test Description"));
    }
}