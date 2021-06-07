package it.enel.kata.birthday_greetings;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeTest {
    @Test
    void isBirthday() {
        Employee employee = new Employee("Omar", "omar@foobar.com", LocalDate.of(1995, 9, 14));
        LocalDate today = LocalDate.of(2021, 9, 14);

        assertThat(employee.isBirthday(today)).isTrue();
    }
}
