package ru.mgrom.roadanalyzer;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;

import ru.mgrom.roadanalyzer.model.ExpenseType;
import ru.mgrom.roadanalyzer.model.PartAndService;
import ru.mgrom.roadanalyzer.model.Spending;
import ru.mgrom.roadanalyzer.repository.ExpenseTypeRepository;
import ru.mgrom.roadanalyzer.repository.PartAndServiceRepository;
import ru.mgrom.roadanalyzer.repository.SpendingRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoadanalyzerApplication {

	public static void main(String[] args) {
		ApplicationContext appContext = SpringApplication.run(RoadanalyzerApplication.class, args);
		initTestData(appContext);
	}

	private static void initTestData(ApplicationContext appContext) {
		ExpenseTypeRepository expTypeRepository = appContext.getBean(ExpenseTypeRepository.class);
		PartAndServiceRepository partRepository = appContext.getBean(PartAndServiceRepository.class);
		SpendingRepository spendingRepository = appContext.getBean(SpendingRepository.class);

		List<ExpenseType> expenseTypes = Stream.of("топливо", "услуги", "запчасти").map(description -> {
			ExpenseType expenseType = new ExpenseType();
			expenseType.setDescription(description);
			return expenseType;
		}).collect(Collectors.toList());
		expTypeRepository.saveAll(expenseTypes);

		Long[] typeId = { 1L };
		List<PartAndService> partAndServices = Stream
				.of("Автобензин АИ-95", "Мойка машины", "Тормозные колодки передние комплект TRW GDB2108")
				.map(description -> {
					PartAndService partAndService = new PartAndService();
					partAndService.setDescription(description);
					partAndService.setType(typeId[0]++);
					return partAndService;
				}).collect(Collectors.toList());
		partRepository.saveAll(partAndServices);

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
		spendingRepository.saveAll(List.of(spending1, spending2, spending3));
	}
}
