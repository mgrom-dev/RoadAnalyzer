package ru.mgrom.roadanalyzer;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import lombok.Data;

public class CsvToSqlMigration {

    @Data
    private static class Spending {
        String date;
        Integer partAndServiceId;
        String description;
        Double count;
        Double amount;
    }

    private static final String INSERT_TEMPLATE = "INSERT INTO admin_db.part_and_service (date, description, quantity, amount, type) VALUES ('%s', '%s', %d, %.2f, %d);";

    private Map<String, Integer> expenseTypeMap = new HashMap<>() {
        {
            put("топливо", 1);
            put("услуги", 2);
            put("запчасти", 3);
        }
    };

    private Map<String, Integer> partAndServiceMap = new HashMap<>();

    private List<Spending> spendings = new ArrayList<>();

    private void parseCsv(String filePath, String outPath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine = reader.readNext();
            parseHeading(nextLine);

            while ((nextLine = reader.readNext()) != null) {
                processRow(nextLine);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    private void saveSqlToFile(String outPath) {
        StringBuilder sqlData = new StringBuilder();
        sqlData.append("INSERT INTO\r\n" + //
                "    admin_db.part_and_service (description, type)\r\n" + //
                "VALUES\r\n");
    }

    private void parseHeading(String[] row) {
        for (int i = 5; i < row.length; i++) {
            expenseTypeMap.put(row[i].trim(), i - 1);
        }
    }

    private void processRow(String[] row) {
        String date = row[0].trim();
        String description = row[1].trim();
        double amount = getAmount(row);

        // Extract count and name from description
        String[] descriptionParts = extractDescriptionAndQuantity(description);
        String itemName = descriptionParts[0];
        int count = Integer.parseInt(descriptionParts[1]);
        String additionalDescription = descriptionParts[2];

        // Генерация SQL-запросов для каждого типа расхода
        for (int i = 3; i < row.length; i++) {
            if (!row[i].trim().isEmpty()) {
                int expenseTypeId = expenseTypeMap.get(getHeader(i));
                String sql = String.format(INSERT_TEMPLATE, date, itemName, count, amount, expenseTypeId);
                System.out.println(sql); // Здесь можно заменить на запись в файл или другую обработку
            }
        }
    }

    private double getAmount(String[] row) {
        for (int i = 2; i < row.length; i++) {
            if (!row[i].trim().isEmpty()) {
                return parseAmount(row[i]);
            }
        }
        return 0.0; // default
    }

    private double parseAmount(String amountStr) {
        return Double.parseDouble(amountStr.replaceAll("[^\\d.,]", "").replace(',', '.'));
    }

    private String[] extractDescriptionAndQuantity(String description) {
        // Обновленное регулярное выражение для захвата текста в скобках
        Pattern pattern = Pattern.compile("(.*?)(\\s*(\\d+)(шт|л)?)?\\s*(\\((.*?)\\))?$");
        Matcher matcher = pattern.matcher(description);
    
        if (matcher.find()) {
            String itemName = matcher.group(1).trim(); // Основное наименование
            String quantityStr = matcher.group(3); // Количество
            String additionalDescription = matcher.group(6); // Описание в скобках
    
            int quantity = (quantityStr != null) ? Integer.parseInt(quantityStr) : 1; // По умолчанию 1
            
            // Возвращаем наименование, количество и описание в скобках
            return new String[] { itemName, String.valueOf(quantity), additionalDescription != null ? additionalDescription.trim() : null };
        }
    
        return new String[] { description, "1", null }; // По умолчанию
    }

    private String getHeader(int index) {
        switch (index) {
            case 2:
                return "Прочие расходы";
            case 3:
                return "Бензин";
            case 4:
                return "Запчасти, расходники";
            case 5:
                return "Услуги автосервисов";
            case 6:
                return "Мойка";
            case 7:
                return "Комплектующие";
            case 8:
                return "Шиномонтаж";
            case 9:
                return "Страхование";
            default:
                return "";
        }
    }

    public static void parseCsvToSql(String srcPath, String outPath) {
        CsvToSqlMigration migration = new CsvToSqlMigration();
        migration.parseCsv(srcPath, outPath);
    }
}