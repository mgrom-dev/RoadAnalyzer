package ru.mgrom.roadanalyzer.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mgrom.roadanalyzer.model.Spending;

public interface SpendingRepository extends JpaRepository<Spending, Long> {
    // select * from Spending where created_at > $1 and created_at < $2
    List<Spending> findByDateBetween(LocalDate min, LocalDate max);
}
