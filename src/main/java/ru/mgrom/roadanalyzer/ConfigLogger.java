package ru.mgrom.roadanalyzer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigLogger {

    @Value("${test.message}")
    private String testMessage;

    public void printConfig() {
        System.out.println("Loaded configuration: " + testMessage);
    }
}