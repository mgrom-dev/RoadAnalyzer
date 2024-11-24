package ru.mgrom.roadanalyzer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.PartAndService;
import ru.mgrom.roadanalyzer.service.PartAndServiceService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class PartAndServiceControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PartAndServiceService partAndServiceService;

    @MockBean
    private HttpServletRequest request;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<PartAndService> parts = List.of(new PartAndService(), new PartAndService());
        when(partAndServiceService.getAll(anyString())).thenReturn(parts);

        mockMvc.perform(get("/api/part_and_service"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testCreate() throws Exception {
        PartAndService partAndService = new PartAndService();
        when(partAndServiceService.create(any(PartAndService.class), anyString())).thenReturn(1L);

        mockMvc.perform(post("/api/part_and_service")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(partAndService)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Part and service created successfully"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(partAndServiceService).delete(anyLong(), anyString());

        mockMvc.perform(delete("/api/part_and_service/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}