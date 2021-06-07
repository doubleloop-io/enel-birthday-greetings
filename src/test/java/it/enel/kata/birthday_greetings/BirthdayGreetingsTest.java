package it.enel.kata.birthday_greetings;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.enel.kata.birthday_greetings.support.LocalSmtpServer;
import it.enel.kata.birthday_greetings.support.MailInfo;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BirthdayGreetingsTest {
    private LocalSmtpServer localSmtpServer;
    private FileConfig fileConfig;
    private SmtpConfig smtpConfig;

    @BeforeEach
    void setUp() {
        localSmtpServer = new LocalSmtpServer();
        localSmtpServer.start();
        fileConfig = new FileConfig(Path.of("employees_test.csv"));
        smtpConfig = new SmtpConfig("127.0.0.1", 3025);
    }

    @AfterEach
    void tearDown() {
        localSmtpServer.stop();
    }

    @Test
    void oneGreeting() throws IOException, MessagingException {
        Files.write(fileConfig.getEmployeesFilePath(),
                Arrays.asList(
                        "last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com",
                        "Ann, Mary, 1975/09/11, mary.ann@foobar.com"),
                StandardCharsets.US_ASCII);

        new BirthdayGreetings(fileConfig, smtpConfig).send(LocalDate.of(2021, 10, 8));

        assertThat(localSmtpServer.receivedMessages()).hasSize(1);
        MailInfo receivedMessage = localSmtpServer.receivedMessages()[0];
        MailInfo expected = new MailInfo(
                "no-reply@foobar.com",
                "john.doe@foobar.com",
                "Happy birthday!",
                "Happy birthday, dear John!");
        assertEquals(expected, receivedMessage);
    }

    @Test
    void noGreetings() throws IOException, MessagingException {
        Files.write(fileConfig.getEmployeesFilePath(),
                Arrays.asList(
                        "last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com",
                        "Ann, Mary, 1975/09/11, mary.ann@foobar.com",
                        "Vallotti, Andrea, 1977/09/11, andrea.vallotti@foobar.com"
                ),
                StandardCharsets.US_ASCII);

        new BirthdayGreetings(fileConfig, smtpConfig).send(LocalDate.of(2021, 9, 12));

        assertThat(localSmtpServer.receivedMessages()).isEmpty();
    }

    @Test
    void manyGreetings() throws IOException, MessagingException {
        Files.write(fileConfig.getEmployeesFilePath(),
                Arrays.asList(
                        "last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com",
                        "Ann, Mary, 1975/09/11, mary.ann@foobar.com",
                        "Vallotti, Andrea, 1977/09/11, andrea.vallotti@foobar.com"
                ),
                StandardCharsets.US_ASCII);

        new BirthdayGreetings(fileConfig, smtpConfig).send(LocalDate.of(2021, 9, 11));

        assertThat(localSmtpServer.receivedMessages()).hasSize(2);
        assertEquals(
                new MailInfo(
                        "no-reply@foobar.com",
                        "mary.ann@foobar.com",
                        "Happy birthday!",
                        "Happy birthday, dear Mary!"),
                localSmtpServer.receivedMessages()[0]);
        assertEquals(
                new MailInfo(
                        "no-reply@foobar.com",
                        "andrea.vallotti@foobar.com",
                        "Happy birthday!",
                        "Happy birthday, dear Andrea!"),
                localSmtpServer.receivedMessages()[1]);
    }
}
