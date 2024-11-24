package ru.mgrom.roadanalyzer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.PartAndService;
import ru.mgrom.roadanalyzer.service.PartAndServiceService;
import ru.mgrom.roadanalyzer.service.SessionUtils;

@WebMvcTest(PartAndServiceController.class)
public class PartAndServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PartAndServiceService partAndServiceService;

    @InjectMocks
    private PartAndServiceController partAndServiceController;

    private final String databaseId = "testDB";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() throws Exception {
        // Arrange
        List<PartAndService> parts = Arrays.asList(new PartAndService(), new PartAndService());
        when(partAndServiceService.getAll(databaseId)).thenReturn(parts);

        // Act & Assert
        mockMvc.perform(get("/api/part_and_service")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2))); // Проверка количества элементов
    }


    
}
