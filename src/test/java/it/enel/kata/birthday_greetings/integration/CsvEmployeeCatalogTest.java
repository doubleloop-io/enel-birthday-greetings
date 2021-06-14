package it.enel.kata.birthday_greetings.integration;

import it.enel.kata.birthday_greetings.domain.BirthDate;
import it.enel.kata.birthday_greetings.domain.Employee;
import it.enel.kata.birthday_greetings.domain.EmployeeCatalog;
import it.enel.kata.birthday_greetings.domain.LoadEmployeesException;
import it.enel.kata.birthday_greetings.infrastructure.CsvEmployeeCatalog;
import it.enel.kata.birthday_greetings.infrastructure.FileConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CsvEmployeeCatalogTest {
    private FileConfig fileConfig;
    private CsvEmployeeCatalog csvEmployeeCatalog;

    @BeforeEach
    void setUp() {
        fileConfig = new FileConfig(Path.of("employees_test.csv"));
        csvEmployeeCatalog = new CsvEmployeeCatalog(fileConfig);
    }

    @Test
    void oneEmployee() {
        EmployeeCatalog employeeCatalog = employeeCatalogWith(
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8))
        );

        Employee[] employees = employeeCatalog.loadAll();

        assertThat(employees).contains(
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)));
    }

    private EmployeeCatalog employeeCatalogWith(Employee... employees) {
        try {
            List<String> lines = Stream.concat(
                    Stream.of("last_name, first_name, date_of_birth, email"),
                    Arrays.stream(employees).map(this::employeeLine)
            ).collect(Collectors.toList());
            Files.write(fileConfig.employeesFilePath(), lines, StandardCharsets.US_ASCII);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new CsvEmployeeCatalog(fileConfig);
    }

    private String employeeLine(Employee employee) {
        String[] parts = new String[] {
                "USELESS",
                employee.name(),
                employee.birthDate().format("yyyy/MM/dd"),
                employee.email()
        };
        return String.join(", ", parts);
    }

    @Test
    void manyEmployees() {
        EmployeeCatalog employeeCatalog = employeeCatalogWith(
                new Employee("Andrea", "andrea.vallotti@foobar.com", BirthDate.of(1977, 12, 27)),
                new Employee("Carlo", "carlo.didomenico@foobar.com", BirthDate.of(1982, 6, 7)),
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8))
        );

        Employee[] employees = employeeCatalog.loadAll();

        assertThat(employees).contains(
                new Employee("Andrea", "andrea.vallotti@foobar.com", BirthDate.of(1977, 12, 27)),
                new Employee("Carlo", "carlo.didomenico@foobar.com", BirthDate.of(1982, 6, 7)),
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)));
    }

    @Test
    void noEmployees() {
        EmployeeCatalog employeeCatalog = employeeCatalogWith();

        assertThatThrownBy(() -> employeeCatalog.loadAll())
                .isInstanceOf(LoadEmployeesException.class);
    }

    @Test
    void emptyFile() throws IOException {
        Files.write(fileConfig.employeesFilePath(), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);

        assertThatThrownBy(() -> csvEmployeeCatalog.loadAll())
                .isInstanceOf(LoadEmployeesException.class);
    }

    @Test
    void fileNotFound() {
        CsvEmployeeCatalog csvEmployeeCatalog = new CsvEmployeeCatalog(new FileConfig(Path.of("DOESNT_EXIST.csv")));

        assertThatThrownBy(() -> csvEmployeeCatalog.loadAll())
                .isInstanceOf(LoadEmployeesException.class);
    }

    @Test
    void invalidDateFormat() throws IOException {
        String wrongDateFormatLine = "Di Domenico, Carlo, 07/06/1982, carlo.didomenico@foobar.com";
        Files.write(fileConfig.employeesFilePath(),
                asList("last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com",
                        wrongDateFormatLine,
                        "Vallotti, Andrea, 1977/12/27, andrea.vallotti@foobar.com"),
                StandardCharsets.US_ASCII);

        assertThatThrownBy(() -> csvEmployeeCatalog.loadAll())
                .isInstanceOf(LoadEmployeesException.class)
                .hasMessageContaining("date format")
                .hasMessageContaining("07/06/1982");
    }
}
