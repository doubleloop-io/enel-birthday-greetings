import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import support.LocalSmtpServer;
import support.MailInfo;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BirthdayGreetingsTest {
    private LocalSmtpServer localSmtpServer;

    @BeforeEach
    void setUp() {
        localSmtpServer = new LocalSmtpServer();
        localSmtpServer.start();
    }

    @AfterEach
    void tearDown() {
        localSmtpServer.stop();
    }

    @Test
    void oneGreeting() throws IOException, MessagingException {
        //Arrange
        Files.write(Path.of("employee.csv"),
                Arrays.asList(
                        "last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com",
                        "Ann, Mary, 1975/09/11, mary.ann@foobar.com"),
                StandardCharsets.US_ASCII);

        //Act - Send greetings Today: 2021/10/08
        new BirthdayGreetings().send(LocalDate.of(2021, 10, 8));

        //Assert
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
        Files.write(Path.of("employee.csv"),
                Arrays.asList(
                        "last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com",
                        "Ann, Mary, 1975/09/11, mary.ann@foobar.com",
                        "Vallotti, Andrea, 1977/09/11, andrea.vallotti@foobar.com"
                ),
                StandardCharsets.US_ASCII);

        new BirthdayGreetings().send(LocalDate.of(2021, 9, 12));

        assertThat(localSmtpServer.receivedMessages()).isEmpty();
    }

    @Test
    void manyGreetings() throws IOException, MessagingException {
        Files.write(Path.of("employee.csv"),
                Arrays.asList(
                        "last_name, first_name, date_of_birth, email",
                        "Doe, John, 1982/10/08, john.doe@foobar.com",
                        "Ann, Mary, 1975/09/11, mary.ann@foobar.com",
                        "Vallotti, Andrea, 1977/09/11, andrea.vallotti@foobar.com"
                ),
                StandardCharsets.US_ASCII);

        new BirthdayGreetings().send(LocalDate.of(2021, 9, 11));

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

    @Test
    void sendMail() throws MessagingException {
        //Get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "127.0.0.1");
        properties.setProperty("mail.smtp.port", "3025");
        Session session = Session.getDefaultInstance(properties);

        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.setFrom(new InternetAddress("no-reply@foobar.com"));
        msg.setSubject("Happy birthday!");
        msg.setText("Happy birthday, dear John!");
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("john.doe@foobar.com", false));

        Transport.send(msg);

        MailInfo receivedMessage = localSmtpServer.receivedMessages()[0];
        MailInfo expected = new MailInfo(
                "no-reply@foobar.com",
                "john.doe@foobar.com",
                "Happy birthday!",
                "Happy birthday, dear John!");
        assertEquals(expected, receivedMessage);
    }

}
