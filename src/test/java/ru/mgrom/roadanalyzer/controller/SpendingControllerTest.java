package ru.mgrom.roadanalyzer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.dto.SpendingDTO;
import ru.mgrom.roadanalyzer.model.ExpenseType;
import ru.mgrom.roadanalyzer.model.PartAndService;
import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.service.ExpenseTypeService;
import ru.mgrom.roadanalyzer.service.PartAndServiceService;
import ru.mgrom.roadanalyzer.service.SpendingService;

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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class SpendingControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private SpendingService spendingService;

    @MockBean
    private ExpenseTypeService expenseTypeService;

    @MockBean
    private PartAndServiceService partAndServiceService;

    @MockBean
    private HttpServletRequest request;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<Spending> spendings = List.of(new Spending(LocalDate.now(), 1L, "Fuel", 10.0, 100.0));
        when(spendingService.getAll(any(), any(), anyString())).thenReturn(spendings);

        mockMvc.perform(get("/api/spending")
                .param("createdAtBefore", LocalDate.now().plusDays(1).toString())
                .param("createdAtAfter", LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetAllDTO() throws Exception {
        List<SpendingDTO> spendingDTOs = List
                .of(new SpendingDTO(1L, LocalDate.now(), 1L, "Fuel", 10.0, 100.0, "Fuel Description", null, null));
        when(spendingService.getAllExpanded(any(), any(), anyString())).thenReturn(spendingDTOs);

        mockMvc.perform(get("/api/spending/full")
                .param("createdAtBefore", LocalDate.now().plusDays(1).toString())
                .param("createdAtAfter", LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testCreateNewSpending() throws Exception {
        SpendingDTO spendingDTO = new SpendingDTO(null, LocalDate.now(), null, "Fuel", 10.0, 100.0, "New Fuel Part",
                null, "New Part Type");
        PartAndService partAndService = new PartAndService("New Part Type", 3L);
        partAndService.setId(1L);
        when(spendingService.create(any(Spending.class), anyString())).thenReturn(1L);
        when(expenseTypeService.create(any(ExpenseType.class), anyString())).thenReturn(1L);
        when(partAndServiceService.get(any(PartAndService.class), anyString())).thenReturn(Optional.of(partAndService));

        mockMvc.perform(post("/api/spending")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(spendingDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Spending saved successfully"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testCreateBadRequest() throws Exception {
        SpendingDTO spendingDTO = new SpendingDTO(null, LocalDate.now(), null, "", 10.0, 100.0, "", null, null);

        mockMvc.perform(post("/api/spending")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(spendingDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Не удалось создать расходы: наименование не задано"));
    }

    @Test
    public void testDelete() throws Exception {
        doNothing().when(spendingService).delete(anyLong(), anyString());

        mockMvc.perform(delete("/api/spending/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(spendingService, times(1)).delete(anyLong(), anyString());
    }
}