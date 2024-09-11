package ru.mgrom.roadanalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mgrom.roadanalyzer.model.Spending;

public interface SpendingRepository extends JpaRepository<Spending, Long> {

}
