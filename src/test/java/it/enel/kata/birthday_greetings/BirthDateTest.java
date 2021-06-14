package it.enel.kata.birthday_greetings;

import it.enel.kata.birthday_greetings.domain.BirthDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class BirthDateTest {
    @Test
    void isBirthday() {
        BirthDate employee = BirthDate.of(1995, 9, 14);
        LocalDate today = LocalDate.of(2021, 9, 14);

        assertThat(employee.isBirthday(today)).isTrue();
    }

    @Test
    void isNotBirthday() {
        BirthDate employee = BirthDate.of(1995, 9, 14);
        LocalDate today = LocalDate.of(2021, 9, 15);

        assertThat(employee.isBirthday(today)).isFalse();
    }

    @Test
    void born29thFeb() {
        BirthDate employee = BirthDate.of(2020, 2, 29);
        LocalDate today = LocalDate.of(2021, 2, 28);
        LocalDate leapYear = LocalDate.of(2024, 2, 28);

        assertThat(employee.isBirthday(today)).isTrue();
        assertThat(employee.isBirthday(leapYear)).isFalse();
    }
}
