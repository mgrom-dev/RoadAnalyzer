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
import ru.mgrom.roadanalyzer.model.Info;
import ru.mgrom.roadanalyzer.service.InfoService;

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
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class InfoControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private InfoService infoService;

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
        List<Info> infoList = List.of(new Info("key1", "value1"), new Info("key2", "value2"));
        when(infoService.getAll(anyString())).thenReturn(infoList);

        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void testGetById() throws Exception {
        Info info = new Info("key1", "value1");
        when(infoService.getById(anyLong(), anyString())).thenReturn(Optional.of(info));

        mockMvc.perform(get("/api/info/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.keyInfo").value("key1"))
                .andExpect(jsonPath("$.valueInfo").value("value1"));
    }

    @Test
    public void testGetByIdNotFound() throws Exception {
        when(infoService.getById(anyLong(), anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/info/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSaveCreate() throws Exception {
        Info info = new Info("key1", "value1");
        info.setId(0L);
        when(infoService.create(any(Info.class), anyString())).thenReturn(1L);

        mockMvc.perform(post("/api/info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testSaveUpdate() throws Exception {
        Info info = new Info("key1", "value1");
        info.setId(1L);
        when(infoService.update(any(Info.class), anyString())).thenReturn(true);

        mockMvc.perform(post("/api/info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void testSaveBadRequest() throws Exception {
        Info info = new Info("key1", "value1");
        info.setId(0L);

        mockMvc.perform(post("/api/info")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(info)))
                .andExpect(status().isBadRequest());
    }
}