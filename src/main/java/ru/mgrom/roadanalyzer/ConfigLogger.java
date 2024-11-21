package ru.mgrom.roadanalyzer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Component
public class ConfigLogger {

    @Value("${test.message}")
    private String testMessage;

    public void printConfig() {
        System.out.println("Loaded configuration: " + testMessage);
        System.out.println("Number of lines of code: " + countLinesInDirectory(new File("./")));
    }

    private int countLinesInDirectory(File directory) {
        int lineCount = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) { // recursive search .java files in directory
                    lineCount += countLinesInDirectory(file);
                } else if (file.getName().endsWith(".java")) { // calc count lines
                    lineCount += countLinesInFile(file);
                }
            }
        }
        return lineCount;
    }

    private int countLinesInFile(File file) {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // removing spaces
                // ignoring empty lines and comments
                if (!line.isEmpty() && !line.startsWith("//") && !line.startsWith("/*")) {
                    lines++;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return lines;
    }
}