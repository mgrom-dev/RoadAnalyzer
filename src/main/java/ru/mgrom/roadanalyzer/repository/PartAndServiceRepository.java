package ru.mgrom.roadanalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mgrom.roadanalyzer.model.PartAndService;

public interface PartAndServiceRepository extends JpaRepository<PartAndService, Long> {

}
