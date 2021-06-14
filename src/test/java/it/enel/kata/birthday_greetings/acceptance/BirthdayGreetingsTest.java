package it.enel.kata.birthday_greetings.acceptance;

import it.enel.kata.birthday_greetings.domain.*;
import it.enel.kata.birthday_greetings.infrastructure.SmtpConfig;
import it.enel.kata.birthday_greetings.infrastructure.SmtpMailSender;
import it.enel.kata.birthday_greetings.support.LocalSmtpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Acceptance test - BirthdayGreetings")
public class BirthdayGreetingsTest {
    private LocalSmtpServer localSmtpServer;
    private SmtpMailSender smtpMailSender;

    @BeforeEach
    void setUp() {
        localSmtpServer = new LocalSmtpServer();
        localSmtpServer.start();
        smtpMailSender = new SmtpMailSender(new SmtpConfig("127.0.0.1", 3025));
    }

    @AfterEach
    void tearDown() {
        localSmtpServer.stop();
    }

    @Test
    void oneGreeting() throws MessagingException {
        EmployeeCatalog employeeCatalog = new InMemoryEmployeeCatalog(
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)),
                new Employee("Mary", "mary.ann@foobar.com", BirthDate.of(1975, 9, 11))
        );

        new BirthdayGreetings(employeeCatalog, smtpMailSender).send(LocalDate.of(2021, 10, 8));

        assertThat(localSmtpServer.receivedMessages()).hasSize(1);
        MailInfo receivedMessage = localSmtpServer.receivedMessages()[0];
        MailInfo expected = MailInfo.greetings("John", "john.doe@foobar.com");
        assertEquals(expected, receivedMessage);
    }

    @Test
    void noGreetings() throws MessagingException {
        EmployeeCatalog employeeCatalog = new InMemoryEmployeeCatalog(
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)),
                new Employee("Mary", "mary.ann@foobar.com", BirthDate.of(1975, 9, 11)),
                new Employee("Andrea", "andrea.vallotti@foobar.com", BirthDate.of(1977, 9, 11))
        );

        new BirthdayGreetings(employeeCatalog, smtpMailSender).send(LocalDate.of(2021, 9, 12));

        assertThat(localSmtpServer.receivedMessages()).isEmpty();
    }

    @Test
    void manyGreetings() throws MessagingException {
        EmployeeCatalog employeeCatalog = new InMemoryEmployeeCatalog(
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)),
                new Employee("Mary", "mary.ann@foobar.com", BirthDate.of(1975, 9, 11)),
                new Employee("Andrea", "andrea.vallotti@foobar.com", BirthDate.of(1977, 9, 11))
        );

        new BirthdayGreetings(employeeCatalog, smtpMailSender).send(LocalDate.of(2021, 9, 11));

        assertThat(localSmtpServer.receivedMessages()).hasSize(2);
        assertEquals(
                MailInfo.greetings("Mary", "mary.ann@foobar.com"),
                localSmtpServer.receivedMessages()[0]);
        assertEquals(
                MailInfo.greetings("Andrea", "andrea.vallotti@foobar.com"),
                localSmtpServer.receivedMessages()[1]);
    }

}
