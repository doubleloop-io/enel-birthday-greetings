package it.enel.kata.birthday_greetings.acceptance;

import it.enel.kata.birthday_greetings.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Acceptance test - BirthdayGreetings")
public class BirthdayGreetingsTest {
    @Test
    void oneGreeting() {
        EmployeeCatalog employeeCatalog = new InMemoryEmployeeCatalog(
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)),
                new Employee("Mary", "mary.ann@foobar.com", BirthDate.of(1975, 9, 11))
        );
        MailSenderSpy mailSender = new MailSenderSpy();

        new BirthdayGreetings(employeeCatalog, mailSender).send(LocalDate.of(2021, 10, 8));

        assertThat(mailSender.receivedMails()).containsOnly(
                MailInfo.greetings("John", "john.doe@foobar.com"));
    }

    @Test
    void noGreetings() {
        EmployeeCatalog employeeCatalog = new InMemoryEmployeeCatalog(
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)),
                new Employee("Mary", "mary.ann@foobar.com", BirthDate.of(1975, 9, 11)),
                new Employee("Andrea", "andrea.vallotti@foobar.com", BirthDate.of(1977, 9, 11))
        );
        MailSenderSpy mailSender = new MailSenderSpy();

        new BirthdayGreetings(employeeCatalog, mailSender).send(LocalDate.of(2021, 9, 12));

        assertThat(mailSender.receivedMails()).isEmpty();
    }

    @Test
    void manyGreetings() {
        EmployeeCatalog employeeCatalog = new InMemoryEmployeeCatalog(
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)),
                new Employee("Mary", "mary.ann@foobar.com", BirthDate.of(1975, 9, 11)),
                new Employee("Andrea", "andrea.vallotti@foobar.com", BirthDate.of(1977, 9, 11))
        );
        MailSenderSpy mailSender = new MailSenderSpy();

        new BirthdayGreetings(employeeCatalog, mailSender).send(LocalDate.of(2021, 9, 11));

        assertThat(mailSender.receivedMails()).containsOnly(
                MailInfo.greetings("Mary", "mary.ann@foobar.com"),
                MailInfo.greetings("Andrea", "andrea.vallotti@foobar.com")
        );
    }

    @Test
    void noEmployees() {
        EmployeeCatalog employeeCatalog = new InMemoryEmployeeCatalog();
        MailSenderSpy mailSender = new MailSenderSpy();

        new BirthdayGreetings(employeeCatalog, mailSender).send(LocalDate.of(2021, 9, 12));

        assertThat(mailSender.receivedMails()).isEmpty();
    }
}
