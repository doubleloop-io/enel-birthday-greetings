package it.enel.kata.birthday_greetings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvEmployeeCatalogTest {
    private FileConfig fileConfig;
    private CsvEmployeeCatalog csvEmployeeCatalog;

    @BeforeEach
    void setUp() {
        fileConfig = new FileConfig(Path.of("employees_test.csv"));
        csvEmployeeCatalog = new CsvEmployeeCatalog(fileConfig);
    }

    @Test
    @Disabled
    void oneEmployee() throws IOException {
        Files.write(fileConfig.getEmployeesFilePath(),
                Arrays.asList(
                        "last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com"),
                StandardCharsets.US_ASCII);

        Employee[] employees = csvEmployeeCatalog.loadEmployees();

        assertThat(employees).contains(
                new Employee("John", "john.doe@foobar.com", new BirthDate(LocalDate.of(1982, 10, 8))));
    }
}
