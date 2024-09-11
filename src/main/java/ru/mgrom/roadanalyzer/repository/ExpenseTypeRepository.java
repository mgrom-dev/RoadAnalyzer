package ru.mgrom.roadanalyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mgrom.roadanalyzer.model.ExpenseType;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {

}
