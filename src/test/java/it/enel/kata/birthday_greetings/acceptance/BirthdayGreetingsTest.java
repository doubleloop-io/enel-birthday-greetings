package it.enel.kata.birthday_greetings.acceptance;

import it.enel.kata.birthday_greetings.domain.BirthdayGreetings;
import it.enel.kata.birthday_greetings.domain.MailInfo;
import it.enel.kata.birthday_greetings.infrastructure.CsvEmployeeCatalog;
import it.enel.kata.birthday_greetings.infrastructure.FileConfig;
import it.enel.kata.birthday_greetings.infrastructure.SmtpConfig;
import it.enel.kata.birthday_greetings.infrastructure.SmtpMailSender;
import it.enel.kata.birthday_greetings.support.LocalSmtpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Acceptance test - BirthdayGreetings")
public class BirthdayGreetingsTest {
    private LocalSmtpServer localSmtpServer;
    private FileConfig fileConfig;
    private CsvEmployeeCatalog csvEmployeeCatalog;
    private SmtpMailSender smtpMailSender;

    @BeforeEach
    void setUp() {
        localSmtpServer = new LocalSmtpServer();
        localSmtpServer.start();
        fileConfig = new FileConfig(Path.of("employees_test.csv"));
        csvEmployeeCatalog = new CsvEmployeeCatalog(fileConfig);
        smtpMailSender = new SmtpMailSender(new SmtpConfig("127.0.0.1", 3025));
    }

    @AfterEach
    void tearDown() {
        localSmtpServer.stop();
    }

    @Test
    void oneGreeting() throws IOException, MessagingException {
        Files.write(fileConfig.employeesFilePath(),
                Arrays.asList(
                        "last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com",
                        "Ann, Mary, 1975/09/11, mary.ann@foobar.com"),
                StandardCharsets.US_ASCII);

        new BirthdayGreetings(csvEmployeeCatalog, smtpMailSender).send(LocalDate.of(2021, 10, 8));

        assertThat(localSmtpServer.receivedMessages()).hasSize(1);
        MailInfo receivedMessage = localSmtpServer.receivedMessages()[0];
        MailInfo expected = MailInfo.greetings("John", "john.doe@foobar.com");
        assertEquals(expected, receivedMessage);
    }

    @Test
    void noGreetings() throws IOException, MessagingException {
        Files.write(fileConfig.employeesFilePath(),
                Arrays.asList(
                        "last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com",
                        "Ann, Mary, 1975/09/11, mary.ann@foobar.com",
                        "Vallotti, Andrea, 1977/09/11, andrea.vallotti@foobar.com"
                ),
                StandardCharsets.US_ASCII);

        new BirthdayGreetings(csvEmployeeCatalog, smtpMailSender).send(LocalDate.of(2021, 9, 12));

        assertThat(localSmtpServer.receivedMessages()).isEmpty();
    }

    @Test
    void manyGreetings() throws IOException, MessagingException {
        Files.write(fileConfig.employeesFilePath(),
                Arrays.asList(
                        "last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com",
                        "Ann, Mary, 1975/09/11, mary.ann@foobar.com",
                        "Vallotti, Andrea, 1977/09/11, andrea.vallotti@foobar.com"
                ),
                StandardCharsets.US_ASCII);

        new BirthdayGreetings(csvEmployeeCatalog, smtpMailSender).send(LocalDate.of(2021, 9, 11));

        assertThat(localSmtpServer.receivedMessages()).hasSize(2);
        assertEquals(
                MailInfo.greetings("Mary", "mary.ann@foobar.com"),
                localSmtpServer.receivedMessages()[0]);
        assertEquals(
                MailInfo.greetings("Andrea", "andrea.vallotti@foobar.com"),
                localSmtpServer.receivedMessages()[1]);
    }
}
