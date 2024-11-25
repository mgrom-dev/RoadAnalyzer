package ru.mgrom.roadanalyzer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.mgrom.roadanalyzer.model.PartAndService;
import ru.mgrom.roadanalyzer.repository.PartAndServiceRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PartAndServiceServiceTest {

    @Mock
    private PartAndServiceRepository repository;

    private PartAndServiceService service;

    private final String databaseId = "testDB";

    @BeforeEach
    void setUp() {
        service = new PartAndServiceService();
        service.repository = repository;
    }

    @Test
    void testGetByDescription() {
        String description = "Brake Pads";
        Long typeId = 1L;
        PartAndService expectedPartAndService = new PartAndService(description, typeId);

        when(repository.getByDescription(description, databaseId)).thenReturn(Optional.of(expectedPartAndService));

        Optional<PartAndService> result = service.getByDescription(description, databaseId);

        assertTrue(result.isPresent());
        assertEquals(description, result.get().getDescription());
        verify(repository, times(1)).getByDescription(description, databaseId);
    }

    @Test
    void testGetAll() {
        List<PartAndService> expectedList = List.of(new PartAndService("Part 1", 1L), new PartAndService("Part 2", 2L));

        when(repository.findAll(databaseId)).thenReturn(expectedList);

        List<PartAndService> result = service.getAll(databaseId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll(databaseId);
    }

    @Test
    void testCreate() {
        PartAndService partAndService = new PartAndService();
        Long expectedId = 1L;

        when(repository.save(partAndService, databaseId)).thenReturn(expectedId);

        Long result = service.create(partAndService, databaseId);

        assertEquals(expectedId, result);
        verify(repository, times(1)).save(partAndService, databaseId);
    }

    @Test
    void testUpdate() {
        PartAndService partAndService = new PartAndService();

        when(repository.update(partAndService, databaseId)).thenReturn(true);

        boolean result = service.update(partAndService, databaseId);

        assertTrue(result);
        verify(repository, times(1)).update(partAndService, databaseId);
    }

    @Test
    void testDelete() {
        PartAndService partAndService = new PartAndService();

        service.delete(partAndService, databaseId);

        verify(repository, times(1)).delete(partAndService, databaseId);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;

        service.delete(id, databaseId);

        verify(repository, times(1)).deleteById(id, databaseId);
    }
}