package it.enel.kata.birthday_greetings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static java.util.Arrays.asList;
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
    void oneEmployee() throws IOException {
        Files.write(fileConfig.getEmployeesFilePath(),
                asList("last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com"),
                StandardCharsets.US_ASCII);

        Employee[] employees = csvEmployeeCatalog.loadEmployees();

        assertThat(employees).contains(
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)));
    }

    @Test
    void manyEmployees() throws IOException {
        Files.write(fileConfig.getEmployeesFilePath(),
                asList("last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com",
                        "Di Domenico, Carlo, 1982/06/07, carlo.didomenico@foobar.com",
                        "Vallotti, Andrea, 1977/12/27, andrea.vallotti@foobar.com"),
                StandardCharsets.US_ASCII);

        Employee[] employees = csvEmployeeCatalog.loadEmployees();

        assertThat(employees).contains(
                new Employee("Andrea", "andrea.vallotti@foobar.com", BirthDate.of(1977, 12, 27)),
                new Employee("Carlo", "carlo.didomenico@foobar.com", BirthDate.of(1982, 6, 7)),
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)));
    }

    @Test
    void noEmployees() throws IOException {
        Files.write(fileConfig.getEmployeesFilePath(),
                asList("last_name, first_name, date_of_birth, email"),
                StandardCharsets.US_ASCII);

        Employee[] employees = csvEmployeeCatalog.loadEmployees();

        assertThat(employees).isEmpty();
    }
}
