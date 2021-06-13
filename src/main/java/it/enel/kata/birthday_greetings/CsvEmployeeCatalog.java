package it.enel.kata.birthday_greetings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CsvEmployeeCatalog {
    public final FileConfig fileConfig;

    public CsvEmployeeCatalog(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    public LocalDate parseDate(String date) {
        return LocalDate.parse(
                date,
                DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public Employee parseEmployeeLine(String line) {
        String[] employeeParts = Arrays.stream(line.split(","))
                .map(String::trim)
                .toArray(String[]::new);
        return new Employee(employeeParts[1], employeeParts[3], new BirthDate(parseDate(employeeParts[2])));
    }
}
