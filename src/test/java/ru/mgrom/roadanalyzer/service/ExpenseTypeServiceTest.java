package ru.mgrom.roadanalyzer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import ru.mgrom.roadanalyzer.model.ExpenseType;
import ru.mgrom.roadanalyzer.repository.ExpenseTypeRepository;

@ExtendWith(MockitoExtension.class)
public class ExpenseTypeServiceTest {
   
    @Mock
    private ExpenseTypeRepository repository;

    private ExpenseTypeService expenseTypeService;

    private final String databaseId = "testDB";

    @BeforeEach
    void setUp() {
        expenseTypeService = new ExpenseTypeService();
        expenseTypeService.repository = repository;
    }

    @Test
    void testGetByDescription() {
        // Settings
        String description = "Fuel";
        ExpenseType expectedExpenseType = new ExpenseType(description);

        when(repository.getByDescription(description, databaseId)).thenReturn(Optional.of(expectedExpenseType));

        // Act
        Optional<ExpenseType> result = expenseTypeService.getByDescription(description, databaseId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(description, result.get().getDescription());
        verify(repository, times(1)).getByDescription(description, databaseId);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        ExpenseType expectedExpenseType = new ExpenseType();
        expectedExpenseType.setId(id);

        when(repository.findById(id, databaseId)).thenReturn(Optional.of(expectedExpenseType));

        Optional<ExpenseType> result = expenseTypeService.getById(id, databaseId);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(repository, times(1)).findById(id, databaseId);
    }

    @Test
    void testGetAll() {
        List<ExpenseType> expectedList = List.of(new ExpenseType(), new ExpenseType());

        when(repository.findAll(databaseId)).thenReturn(expectedList);

        List<ExpenseType> result = expenseTypeService.getAll(databaseId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll(databaseId);
    }

    @Test
    void testCreate() {
        ExpenseType expenseType = new ExpenseType();
        Long expectedId = 1L;

        when(repository.save(expenseType, databaseId)).thenReturn(expectedId);

        Long result = expenseTypeService.create(expenseType, databaseId);

        assertEquals(expectedId, result);
        verify(repository, times(1)).save(expenseType, databaseId);
    }

    @Test
    void testUpdate() {
        ExpenseType expenseType = new ExpenseType();

        when(repository.update(expenseType, databaseId)).thenReturn(true);

        boolean result = expenseTypeService.update(expenseType, databaseId);

        assertTrue(result);
        verify(repository, times(1)).update(expenseType, databaseId);
    }

    @Test
    void testDelete() {
        ExpenseType expenseType = new ExpenseType();

        expenseTypeService.delete(expenseType, databaseId);

        verify(repository, times(1)).delete(expenseType, databaseId);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;

        expenseTypeService.delete(id, databaseId);

        verify(repository, times(1)).deleteById(id, databaseId);
    }
}