package ru.mgrom.roadanalyzer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PartAndServiceTest {
   
    @Test
    void testConstructorAndGetterSetter() {
        // Settings
        String description = "spare part";
        Long type = 1L;

        // Act
        PartAndService partAndService = new PartAndService(description, type);

        // Assert
        assertEquals(description, partAndService.getDescription());
        assertEquals(type, partAndService.getType());
    }

    @Test
    void testNoArgsConstructor() {
        PartAndService partAndService = new PartAndService();

        assertNull(partAndService.getDescription());
        assertNull(partAndService.getType());
    }

    @Test
    void testSetters() {
        PartAndService partAndService = new PartAndService();

        partAndService.setDescription("New Description");
        partAndService.setType(5L);

        assertEquals("New Description", partAndService.getDescription());
        assertEquals(5L, partAndService.getType());
    }

    @Test
    void testToString() {
        PartAndService partAndService = new PartAndService("Test", 1L);

        String toString = partAndService.toString();

        assertTrue(toString.contains("description=Test"));
        assertTrue(toString.contains("type=1"));
    }
}
