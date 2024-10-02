package ru.mgrom.roadanalyzer.model;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ru.mgrom.roadanalyzer.repository.PartAndServiceRepository;
import ru.mgrom.roadanalyzer.service.PartAndServiceService;

@ActiveProfiles("test")
@SpringBootTest
public class PartAndServiceServiceTest {
    @Autowired
    private PartAndServiceService partAndServiceService;
    @Autowired
    private PartAndServiceRepository partAndServiceRepository;

    @BeforeEach
    void beforeEach() {
        //partAndServiceRepository.findAll();
    }

    @Test
    void getByIdNotFound() {
        // assertTrue(partAndServiceService.getById(1L).isEmpty());
    }

    @Test
    void getByIdOk() {
        // PartAndService partAndService = new PartAndService();
        // partAndService.setDescription("default");
        // partAndService = partAndServiceRepository.save(partAndService);
        // assertTrue(partAndServiceService.getById(partAndService.getId()).isPresent());
    }

    @Test
    void getAll() {
        // List<PartAndService> partAndServices = Stream.of("Автобензин АИ-95", "Автобензин АИ-92", "Автобензин АИ-100")
        //         .map(name -> {
        //             PartAndService ps = new PartAndService();
        //             ps.setDescription(name);
        //             return ps;
        //         })
        //         .collect(Collectors.toList());

        // partAndServiceRepository.saveAll(partAndServices);
        // assertTrue(partAndServiceService.getAll().size() == 3);
    }

    @Test
    void create() {
        // PartAndService partAndService = new PartAndService();
        // partAndService.setDescription("Автобензин АИ-95");
        // partAndService.setType(1L);

        // PartAndService created = partAndServiceService.create(partAndService);
        // assertTrue(partAndService.getDescription().equals(created.getDescription()));
        // assertTrue(partAndService.getType() == created.getType());
    }

    @Test
    void delete() {
        // PartAndService partAndService = new PartAndService();
        // partAndService.setDescription("default");
        // PartAndService created = partAndServiceRepository.save(partAndService);
        // partAndServiceService.delete(created);
        // assertTrue(partAndServiceRepository.findAll().size() == 0);
    }

    @Test
    void update() {
        // PartAndService partAndService = new PartAndService();
        // partAndService.setDescription("Автобензин АИ-95");
        // partAndService.setType(1L);


        // PartAndService newPS = new PartAndService();
        // newPS.setDescription("default");
        // Long id = partAndServiceRepository.save(newPS).getId();
        // PartAndService updated = partAndServiceService.update(id, partAndService).get();
        // assertTrue(partAndService.getDescription().equals(updated.getDescription()));
        // assertTrue(partAndService.getType() == updated.getType());
    }
}
