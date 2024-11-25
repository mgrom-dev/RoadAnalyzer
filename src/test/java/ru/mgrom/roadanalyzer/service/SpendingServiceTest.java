package ru.mgrom.roadanalyzer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.mgrom.roadanalyzer.dto.SpendingDTO;
import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.repository.SpendingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SpendingServiceTest {

    @Mock
    private SpendingRepository repository;

    private SpendingService spendingService;

    private final String databaseId = "testDB";

    @BeforeEach
    void setUp() {
        spendingService = new SpendingService();
        spendingService.repository = repository;
    }

    @Test
    void testGetAll() {
        LocalDate createdAtBefore = LocalDate.of(2024, 1, 1);
        LocalDate createdAtAfter = LocalDate.of(2023, 1, 1);
        List<Spending> expectedSpendings = List.of(
                new Spending(LocalDate.of(2023, 5, 20), 1L, "Fuel", 10.0, 100.0),
                new Spending(LocalDate.of(2023, 6, 15), 2L, "Maintenance", 5.0, 250.0));

        when(repository.findSpendingsByDateRange(createdAtAfter, createdAtBefore, databaseId))
                .thenReturn(expectedSpendings);

        List<Spending> result = spendingService.getAll(createdAtBefore, createdAtAfter, databaseId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findSpendingsByDateRange(createdAtAfter, createdAtBefore, databaseId);
    }

    @Test
    void testGetAllWithNullDates() {
        List<Spending> expectedSpendings = List.of(
                new Spending(LocalDate.of(2023, 5, 20), 1L, "Fuel", 10.0, 100.0));

        when(repository.findSpendingsByDateRange(LocalDate.MAX, LocalDate.MIN, databaseId))
                .thenReturn(expectedSpendings);

        List<Spending> result = spendingService.getAll(null, null, databaseId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findSpendingsByDateRange(LocalDate.MAX, LocalDate.MIN, databaseId);
    }

    @Test
    void testGetAllExpanded() {
        LocalDate createdAtBefore = LocalDate.of(2024, 1, 1);
        LocalDate createdAtAfter = LocalDate.of(2023, 1, 1);

        List<SpendingDTO> expectedSpendingsDTO = List.of(
                new SpendingDTO(1L, LocalDate.of(2023, 5, 20), 1L, "Fuel", 10.0, 100.0,
                        "Fuel Description", null, null),
                new SpendingDTO(2L, LocalDate.of(2023, 6, 15), 2L, "Maintenance", 5.0, 250.0,
                        "Maintenance Description", null, null));

        when(repository.findSpendingsByDateRangeDTO(createdAtAfter, createdAtBefore, databaseId))
                .thenReturn(expectedSpendingsDTO);

        List<SpendingDTO> result = spendingService.getAllExpanded(createdAtBefore, createdAtAfter, databaseId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findSpendingsByDateRangeDTO(createdAtAfter, createdAtBefore, databaseId);
    }

    @Test
    void testGetAllExpandedWithNullDates() {
        List<SpendingDTO> expectedSpendingsDTO = List.of(
                new SpendingDTO(1L, LocalDate.of(2023, 5, 20), 1L, "Fuel", 10.0,
                        100.0, "Fuel Description", null, null));

        when(repository.findSpendingsByDateRangeDTO(LocalDate.MAX, LocalDate.MIN, databaseId))
                .thenReturn(expectedSpendingsDTO);

        List<SpendingDTO> result = spendingService.getAllExpanded(null, null, databaseId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findSpendingsByDateRangeDTO(LocalDate.MAX, LocalDate.MIN, databaseId);
    }

    @Test
    void testGetById() {
        Long id = 1L;
        Spending expectedSpending = new Spending(LocalDate.of(2023, 5, 20), id,
                "Fuel", 10.0,
                100.0);

        when(repository.findById(id, databaseId)).thenReturn(Optional.of(expectedSpending));

        Optional<Spending> result = spendingService.getById(id, databaseId);

        assertTrue(result.isPresent());
        assertEquals(expectedSpending.getDescription(), result.get().getDescription());
        verify(repository, times(1)).findById(id, databaseId);
    }

    @Test
    void testCreate() {
        Spending spending = new Spending(LocalDate.now(), null, "New Part",
                5.0,
                50.0);

        Long expectedId = 2L;

        when(repository.save(spending, databaseId)).thenReturn(expectedId);

        Long result = spendingService.create(spending, databaseId);

        assertEquals(expectedId, result);
        verify(repository, times(1)).save(spending, databaseId);
    }

    @Test
    void testUpdate() {
        Spending spending = new Spending();

        when(repository.update(spending, databaseId)).thenReturn(true);

        boolean result = spendingService.update(spending, databaseId);

        assertTrue(result);
        verify(repository, times(1)).update(spending, databaseId);
    }

    @Test
    void testDelete() {
        Spending spending = new Spending();

        spendingService.delete(spending, databaseId);

        verify(repository, times(1)).delete(spending, databaseId);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;

        spendingService.delete(id, databaseId);

        verify(repository, times(1)).deleteById(id, databaseId);
    }
}