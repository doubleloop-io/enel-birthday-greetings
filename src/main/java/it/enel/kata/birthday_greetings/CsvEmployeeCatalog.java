package it.enel.kata.birthday_greetings;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvEmployeeCatalog {
    private final FileConfig fileConfig;

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

    public Employee[] loadEmployees() throws IOException {
        List<String> lines = Files.readAllLines(fileConfig.getEmployeesFilePath());
        ArrayList<Employee> employees = new ArrayList<>();
        Employee[] ret = lines.stream().skip(1).map(this::parseEmployeeLine).toArray(Employee[]::new);

        for (String line : lines.stream().skip(1).toArray(String[]::new)) {
            employees.add(parseEmployeeLine(line));
        }
        return ret;
    }
}
