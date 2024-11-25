package ru.mgrom.roadanalyzer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.ExpenseType;
import ru.mgrom.roadanalyzer.service.ExpenseTypeService;

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
public class ExpenseTypeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private ExpenseTypeService expenseTypeService;

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
        List<ExpenseType> expenseTypes = List.of(new ExpenseType("Fuel"), new ExpenseType("Maintenance"));
        when(expenseTypeService.getAll(anyString())).thenReturn(expenseTypes);

        mockMvc.perform(get("/api/expense_type"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testCreate() throws Exception {
        ExpenseType expenseType = new ExpenseType("Fuel");
        when(expenseTypeService.create(any(ExpenseType.class), anyString())).thenReturn(1L);

        mockMvc.perform(post("/api/expense_type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expenseType)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Expense type created successfully"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testCreateBadRequest() throws Exception {
        ExpenseType expenseType = new ExpenseType("Fuel");
        when(expenseTypeService.create(any(ExpenseType.class), anyString())).thenReturn(0L);

        mockMvc.perform(post("/api/expense_type")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expenseType)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Failed to create expense type: Invalid data or conflict"))
                .andExpect(jsonPath("$.id").isEmpty());
    }
}