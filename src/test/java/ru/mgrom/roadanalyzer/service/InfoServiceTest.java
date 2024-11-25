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

import ru.mgrom.roadanalyzer.model.Info;
import ru.mgrom.roadanalyzer.repository.InfoRepository;

@ExtendWith(MockitoExtension.class)
public class InfoServiceTest {
   
    @Mock
    private InfoRepository repository;

    private InfoService service;

    private final String databaseId = "testDB";

    @BeforeEach
    void setUp() {
        service = new InfoService();
        service.repository = repository;
    }

    @Test
    void testGetById() {
        Long id = 1L;
        Info expectedInfo = new Info();
        expectedInfo.setId(id);

        when(repository.findById(id, databaseId)).thenReturn(Optional.of(expectedInfo));

        Optional<Info> result = service.getById(id, databaseId);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(repository, times(1)).findById(id, databaseId);
    }

    @Test
    void testGetAll() {
        List<Info> expectedList = List.of(new Info(), new Info());

        when(repository.findAll(databaseId)).thenReturn(expectedList);

        List<Info> result = service.getAll(databaseId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll(databaseId);
    }

    @Test
    void testCreate() {
        Info info = new Info();
        Long expectedId = 1L;

        when(repository.save(info, databaseId)).thenReturn(expectedId);

        Long result = service.create(info, databaseId);

        assertEquals(expectedId, result);
        verify(repository, times(1)).save(info, databaseId);
    }

    @Test
    void testUpdate() {
        Info info = new Info();

        when(repository.update(info, databaseId)).thenReturn(true);

        boolean result = service.update(info, databaseId);

        assertTrue(result);
        verify(repository, times(1)).update(info, databaseId);
    }

    @Test
    void testDelete() {
        Info info = new Info();

        service.delete(info, databaseId);

        verify(repository, times(1)).delete(info, databaseId);
    }

    @Test
    void testDeleteById() {
        Long id = 1L;

        service.delete(id, databaseId);

        verify(repository, times(1)).deleteById(id, databaseId);
    }
}