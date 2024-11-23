package ru.mgrom.roadanalyzer;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Optional;
import java.util.Locale;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParser;

import lombok.AllArgsConstructor;
import lombok.Data;

public class CsvToSqlMigration {

    @Data
    @AllArgsConstructor
    private static class Spending {
        String date;
        Integer partAndServiceId;
        String description;
        Double count;
        Double amount;
    }

    @Data
    @AllArgsConstructor
    private static class PartAndService {
        Integer id;
        String description;
        Integer expenseTypeId;
    }

    private List<String> expenseTypes = new ArrayList<>() {
        {
            add("топливо");
            add("услуги");
            add("запчасти");
        }
    };

    private Map<String, PartAndService> partAndServiceMap = new HashMap<>();

    private List<Spending> spendings = new ArrayList<>();

    private void parseCsv(String filePath, String outPath) {
        CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath)).withCSVParser(csvParser).build()) {
            String[] nextLine = reader.readNext();
            parseHeading(nextLine);

            while ((nextLine = reader.readNext()) != null) {
                processRow(nextLine);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        saveSqlToFile(outPath);
    }

    private void saveSqlToFile(String outPath) {
        StringBuilder sqlData = new StringBuilder();

        sqlData.append("INSERT INTO\r\n" + //
                "    admin_db.expense_type (description)\r\n" + //
                "VALUES");
        expenseTypes.forEach(expenseType -> sqlData.append(String.format("\r\n('%s'),", expenseType)));
        sqlData.setLength(sqlData.length() - 1);
        sqlData.append(";\r\n");

        sqlData.append("INSERT INTO\r\n" + //
                "    admin_db.part_and_service (id, description, type)\r\n" + //
                "VALUES\r\n");
        partAndServiceMap.values().forEach(ps -> sqlData
                .append(String.format("\r\n(%d, '%s', %d),", ps.id, ps.description, ps.expenseTypeId)));
        sqlData.setLength(sqlData.length() - 1);
        sqlData.append(";\r\n");

        DecimalFormat decimalFormat = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US));
        sqlData.append("INSERT INTO\r\n" + //
                "    admin_db.spending (date, part_and_service_id, description, count, amount)\r\n" + //
                "VALUES\r\n");
        spendings.forEach(sp -> sqlData
                .append(String.format("\r\n('%s', %d, %s, %s, %s),",
                        dbDate(sp.date), sp.partAndServiceId,
                        sp.description == null ? "NULL" : "'" + sp.description + "'",
                        decimalFormat.format(sp.count), decimalFormat.format(sp.amount))));
        sqlData.setLength(sqlData.length() - 1);
        sqlData.append(";");

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outPath), "UTF-8"))) {
            writer.write(sqlData.toString());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing in file: " + e.getMessage());
        }
    }

    private String dbDate(String date) {
        String[] partsOfDate = date.split("\\.");
        return partsOfDate[2] + "-" + partsOfDate[1] + "-" + partsOfDate[0];
    }

    private void parseHeading(String[] row) {
        for (int i = 5; i < row.length; i++) {
            expenseTypes.add(row[i].trim());
        }
    }

    private void processRow(String[] row) {
        String date = row[0].trim();
        String description = row[1].trim();
        double amount = getAmount(row);

        // Extract count and name from description
        String[] descriptionParts = extractDescriptionAndQuantity(description);
        String itemName = descriptionParts[0];
        Double count = Double.parseDouble(descriptionParts[1]);
        String additionalDescription = descriptionParts[2];

        // generate new spending
        for (int i = 2; i < row.length; i++) {
            if (!row[i].trim().isEmpty()) {
                int expenseTypeId = i - 1;
                PartAndService partAndService = partAndServiceMap.get(itemName);
                if (partAndService == null) {
                    partAndService = new PartAndService(partAndServiceMap.size() + 1, itemName, expenseTypeId);
                    partAndServiceMap.put(itemName, partAndService);
                }
                Spending newSpending = new Spending(date, partAndService.getId(), additionalDescription, count, amount);
                spendings.add(newSpending);
                break;
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
        Pattern countPattern = Pattern.compile("\\d*\\.?\\,?\\d+(?=шт\\.?|л\\.?)");
        Pattern addDescPattern = Pattern.compile("(?<=\\().+(?=\\))");
        String itemNameRegex = " ?-? ?\\(.+\\) ?| ?\\d*\\.?\\,?\\d+шт.?| ?\\d*\\.?\\,?\\d+л.?|\\s*\\n\\s*";

        String count = Optional.of(countPattern.matcher(description))
                .filter(Matcher::find)
                .map(matcher -> matcher.group())
                .orElse("1").replaceAll(",", ".");
        String additionalDescription = Optional.of(addDescPattern.matcher(description))
                .filter(Matcher::find)
                .map(matcher -> matcher.group())
                .orElse(null);
        String itemName = description.replaceAll(itemNameRegex, "");

        return new String[] { itemName, count, additionalDescription };
    }

    public static void parseCsvToSql(String srcPath, String outPath) {
        CsvToSqlMigration migration = new CsvToSqlMigration();
        migration.parseCsv(srcPath, outPath);
    }
}