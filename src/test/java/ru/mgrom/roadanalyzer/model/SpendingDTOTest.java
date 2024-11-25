package ru.mgrom.roadanalyzer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import ru.mgrom.roadanalyzer.dto.SpendingDTO;

public class SpendingDTOTest {

    @Test
    void testConstructorAndGetterSetter() {
        Long id = 1L;
        LocalDate date = LocalDate.of(2023, 5, 20);
        Long partAndServiceId = 2L;
        String description = "Fuel";
        Double count = 10.0;
        Double amount = 100.0;
        String partDescription = "Fuel Description";
        Long partType = 3L;
        String partTypeDescription = "Part Type Description";

        SpendingDTO spendingDTO = new SpendingDTO(id, date, partAndServiceId, description,
                count, amount, partDescription, partType, partTypeDescription);

        // Проверка
        assertEquals(id, spendingDTO.getId());
        assertEquals(date, spendingDTO.getDate());
        assertEquals(partAndServiceId, spendingDTO.getPartAndServiceId());
        assertEquals(description, spendingDTO.getDescription());
        assertEquals(count, spendingDTO.getCount());
        assertEquals(amount, spendingDTO.getAmount());
        assertEquals(partDescription, spendingDTO.getPartDescription());
        assertEquals(partType, spendingDTO.getPartType());
        assertEquals(partTypeDescription, spendingDTO.getPartTypeDescription());
    }

    @Test
    void testNoArgsConstructor() {
        SpendingDTO spendingDTO = new SpendingDTO();

        assertNull(spendingDTO.getId());
        assertNull(spendingDTO.getDate());
        assertNull(spendingDTO.getPartAndServiceId());
        assertNull(spendingDTO.getDescription());
        assertNull(spendingDTO.getCount());
        assertNull(spendingDTO.getAmount());
        assertNull(spendingDTO.getPartDescription());
        assertNull(spendingDTO.getPartType());
        assertNull(spendingDTO.getPartTypeDescription());
    }

    @Test
    void testSetters() {
        SpendingDTO spendingDTO = new SpendingDTO();

        Long id = 1L;
        LocalDate date = LocalDate.of(2023, 5, 20);
        Long partAndServiceId = 2L;
        String description = "Fuel";
        Double count = 10.0;
        Double amount = 100.0;
        
        spendingDTO.setId(id);
        spendingDTO.setDate(date);
        spendingDTO.setPartAndServiceId(partAndServiceId);
        spendingDTO.setDescription(description);
        spendingDTO.setCount(count);
        spendingDTO.setAmount(amount);
        
        String partDescription = "Fuel Description";
        Long partType = 3L;
        String partTypeDescription = "Part Type Description";

        spendingDTO.setPartDescription(partDescription);
        spendingDTO.setPartType(partType);
        spendingDTO.setPartTypeDescription(partTypeDescription);

        assertEquals(id, spendingDTO.getId());
        assertEquals(date, spendingDTO.getDate());
        assertEquals(partAndServiceId, spendingDTO.getPartAndServiceId());
        assertEquals(description, spendingDTO.getDescription());
        assertEquals(count, spendingDTO.getCount());
        assertEquals(amount, spendingDTO.getAmount());
        
        assertEquals(partDescription, spendingDTO.getPartDescription());
        assertEquals(partType, spendingDTO.getPartType());
        assertEquals(partTypeDescription, spendingDTO.getPartTypeDescription());
    }
}