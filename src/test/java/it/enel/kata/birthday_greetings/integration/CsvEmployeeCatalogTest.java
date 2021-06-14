package it.enel.kata.birthday_greetings.integration;

import it.enel.kata.birthday_greetings.contract.EmployeeCatalogContractTest;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CsvEmployeeCatalogTest extends EmployeeCatalogContractTest {
    private FileConfig fileConfig;
    private CsvEmployeeCatalog csvEmployeeCatalog;

    @BeforeEach
    void setUp() {
        fileConfig = new FileConfig(Path.of("employees_test.csv"));
        csvEmployeeCatalog = new CsvEmployeeCatalog(fileConfig);
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

    protected EmployeeCatalog employeeCatalogWith(Employee... employees) {
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
}
