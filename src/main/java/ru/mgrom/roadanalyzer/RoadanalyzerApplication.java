package ru.mgrom.roadanalyzer;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ru.mgrom.roadanalyzer.model.ExpenseType;
import ru.mgrom.roadanalyzer.model.PartAndService;
import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.repository.ExpenseTypeRepository;
import ru.mgrom.roadanalyzer.repository.PartAndServiceRepository;
import ru.mgrom.roadanalyzer.repository.SpendingRepository;
import ru.mgrom.roadanalyzer.service.DatabaseSchemaService;

@SpringBootApplication
public class RoadanalyzerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(RoadanalyzerApplication.class, args);

		// prepare test data
		String databaseIdentifier = "admin_db";

		DatabaseSchemaService databaseSchemaService = context.getBean(DatabaseSchemaService.class);
		databaseSchemaService.createSchemaIfNotExists(databaseIdentifier);

		ExpenseTypeRepository expenseTypeRepository = context.getBean(ExpenseTypeRepository.class);
		Stream.of("топливо", "услуги", "запчасти").forEach(description -> {
			ExpenseType expenseType = new ExpenseType();
			expenseType.setDescription(description);
			expenseTypeRepository.save(expenseType, databaseIdentifier);
		});

		PartAndServiceRepository partAndServiceRepository = context.getBean(PartAndServiceRepository.class);
		Long[] typeId = { 1L };
		Stream.of("Автобензин АИ-95", "Мойка машины", "Тормозные колодки передние комплект TRW GDB2108")
				.forEach(description -> {
					PartAndService partAndService = new PartAndService();
					partAndService.setDescription(description);
					partAndService.setType(typeId[0]++);
					partAndServiceRepository.save(partAndService, databaseIdentifier);
				});

		SpendingRepository spendingRepository = context.getBean(SpendingRepository.class);
		Spending spending1 = new Spending();
		spending1.setDate(LocalDate.of(2024, 6, 16));
		spending1.setPartAndServiceId(1L);
		spending1.setCount(30.0);
		spending1.setAmount(1615.15);
		Spending spending2 = new Spending();
		spending2.setDate(LocalDate.of(2024, 6, 20));
		spending2.setPartAndServiceId(2L);
		spending2.setDescription("кузов + коврики");
		spending2.setCount(1.0);
		spending2.setAmount(100.15);
		Spending spending3 = new Spending();
		spending3.setDate(LocalDate.of(2024, 6, 25));
		spending3.setPartAndServiceId(3L);
		spending3.setCount(1.0);
		spending3.setAmount(2400.0);
		spendingRepository.save(spending1, databaseIdentifier);
		spendingRepository.save(spending2, databaseIdentifier);
		spendingRepository.save(spending3, databaseIdentifier);
	}
}
