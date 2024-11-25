package ru.mgrom.roadanalyzer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class SpendingTest {

    @Test
    void testConstructorAndGetterSetter() {
        LocalDate date = LocalDate.of(2023, 5, 20);
        Long partAndServiceId = 1L;
        String description = "Fuel";
        Double count = 10.0;
        Double amount = 100.0;

        Spending spending = new Spending(date, partAndServiceId, description, count, amount);

        assertEquals(date, spending.getDate());
        assertEquals(partAndServiceId, spending.getPartAndServiceId());
        assertEquals(description, spending.getDescription());
        assertEquals(count, spending.getCount());
        assertEquals(amount, spending.getAmount());
    }

    @Test
    void testNoArgsConstructor() {
        Spending spending = new Spending();

        assertNull(spending.getDate());
        assertNull(spending.getPartAndServiceId());
        assertNull(spending.getDescription());
        assertNull(spending.getCount());
        assertNull(spending.getAmount());
    }

    @Test
    void testSetters() {
        Spending spending = new Spending();

        LocalDate date = LocalDate.of(2023, 5, 20);
        Long partAndServiceId = 1L;
        String description = "Fuel";
        Double count = 10.0;
        Double amount = 100.0;

        spending.setDate(date);
        spending.setPartAndServiceId(partAndServiceId);
        spending.setDescription(description);
        spending.setCount(count);
        spending.setAmount(amount);

        assertEquals(date, spending.getDate());
        assertEquals(partAndServiceId, spending.getPartAndServiceId());
        assertEquals(description, spending.getDescription());
        assertEquals(count, spending.getCount());
        assertEquals(amount, spending.getAmount());
    }

    @Test
    void testToString() {
        Spending spending = new Spending(LocalDate.of(2023, 5, 20), 1L,
                "Fuel", 10.0, 100.0);

        String toString = spending.toString();

        assertTrue(toString.contains("date=2023-05-20"));
        assertTrue(toString.contains("partAndServiceId=1"));
        assertTrue(toString.contains("description=Fuel"));
        assertTrue(toString.contains("count=10.0"));
        assertTrue(toString.contains("amount=100.0"));
    }
}