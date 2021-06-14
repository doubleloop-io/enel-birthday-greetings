package it.enel.kata.birthday_greetings.infrastructure;

import it.enel.kata.birthday_greetings.domain.Employee;
import it.enel.kata.birthday_greetings.domain.EmployeeCatalog;
import it.enel.kata.birthday_greetings.domain.LoadEmployeesException;
import it.enel.kata.birthday_greetings.domain.BirthDate;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class CsvEmployeeCatalog implements EmployeeCatalog {
    private final FileConfig fileConfig;

    public CsvEmployeeCatalog(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    public LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(
                    date,
                    DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        } catch (DateTimeParseException e) {
            throw new LoadEmployeesException("Invalid date format: " + date, e);
        }
    }

    public Employee parseEmployeeLine(String line) {
        String[] employeeParts = Arrays.stream(line.split(","))
                .map(String::trim)
                .toArray(String[]::new);
        return new Employee(employeeParts[1], employeeParts[3], new BirthDate(parseDate(employeeParts[2])));
    }

    public Employee[] loadAll() {
        if (!Files.exists(fileConfig.employeesFilePath())) return new Employee[0];
        try {
            return Files.readAllLines(fileConfig.employeesFilePath()).stream()
                    .skip(1)
                    .map(this::parseEmployeeLine)
                    .toArray(Employee[]::new);
        } catch (IOException e) {
            throw new LoadEmployeesException("Error while reading file " + fileConfig.employeesFilePath(), e);
        }
    }
}
