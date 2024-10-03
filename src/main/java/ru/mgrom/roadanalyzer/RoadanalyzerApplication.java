package ru.mgrom.roadanalyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import ru.mgrom.roadanalyzer.service.DatabaseSchemaService;

@SpringBootApplication
public class RoadanalyzerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(RoadanalyzerApplication.class, args);
		DatabaseSchemaService databaseSchemaService = context.getBean(DatabaseSchemaService.class);
		databaseSchemaService.createSchemaIfNotExists("admin_db");
	}
}
