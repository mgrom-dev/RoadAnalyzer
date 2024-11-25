package ru.mgrom.roadanalyzer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InfoTest {

    @Test
    void testConstructorAndGetterSetter() {
        String keyInfo = "Model";
        String valueInfo = "Skoda";

        Info info = new Info(keyInfo, valueInfo);

        assertEquals(keyInfo, info.getKeyInfo());
        assertEquals(valueInfo, info.getValueInfo());
    }

    @Test
    void testNoArgsConstructor() {
        Info info = new Info();

        assertNull(info.getKeyInfo());
        assertNull(info.getValueInfo());
    }

    @Test
    void testSetters() {
        Info info = new Info();

        info.setKeyInfo("New Key");
        info.setValueInfo("New Value");

        assertEquals("New Key", info.getKeyInfo());
        assertEquals("New Value", info.getValueInfo());
    }

    @Test
    void testToString() {
        Info info = new Info("Test Key", "Test Value");

        String toString = info.toString();

        assertTrue(toString.contains("keyInfo=Test Key"));
        assertTrue(toString.contains("valueInfo=Test Value"));
    }
}