package ru.mgrom.roadanalyzer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RoadanalyzerApplication {
	public static void main(String[] args) {
		SpringApplication.run(RoadanalyzerApplication.class, args);
		CsvToSqlMigration.parseCsvToSql("./Skoda Fabia II - spendings.csv", "./new_data.sql");
	}

	@Bean
	public CommandLineRunner commandLineRunner(ConfigLogger configLogger) {
		return args -> {
			configLogger.printConfig(); // Вызов метода для вывода сообщения в консоль
		};
	}
}
