package ru.mgrom.roadanalyzer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import ru.mgrom.roadanalyzer.model.Info;
import ru.mgrom.roadanalyzer.service.InfoService;
import ru.mgrom.roadanalyzer.service.SessionUtils;

@RestController
@RequestMapping("/api/info")
public class InfoController {
    @Autowired
    private InfoService infoService;

    private String databaseId;

    @ModelAttribute
    public void setDatabaseId(HttpServletRequest request) {
        this.databaseId = SessionUtils.getDatabaseId(request);
    }

    @GetMapping
    public ResponseEntity<List<Info>> getAll() {
        return ResponseEntity.ok(infoService.getAll(databaseId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Info> getById(@PathVariable Long id) {
        return infoService.getById(id, databaseId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Info> save(@RequestBody Info info) {
        return info.getId() > 0 ? updateInfo(info) : createInfo(info);
    }

    private ResponseEntity<Info> updateInfo(Info info) {
        return infoService.update(info, databaseId)
                ? ResponseEntity.ok(info)
                : ResponseEntity.badRequest().build();
    }

    private ResponseEntity<Info> createInfo(Info info) {
        Long savedId = infoService.create(info, databaseId);
        if (savedId > 0) {
            info.setId(savedId);
            return ResponseEntity.ok(info);
        }
        return ResponseEntity.badRequest().build();
    }
}
