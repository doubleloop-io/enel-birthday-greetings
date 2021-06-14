package it.enel.kata.birthday_greetings.integration;

import it.enel.kata.birthday_greetings.domain.MailInfo;
import it.enel.kata.birthday_greetings.domain.NoDeliveryException;
import it.enel.kata.birthday_greetings.infrastructure.SmtpConfig;
import it.enel.kata.birthday_greetings.infrastructure.SmtpMailSender;
import it.enel.kata.birthday_greetings.support.LocalSmtpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SmtpMailSenderTest {
    private LocalSmtpServer localSmtpServer;
    private SmtpConfig smtpConfig;

    @BeforeEach
    void setUp() {
        localSmtpServer = new LocalSmtpServer();
        localSmtpServer.start();
        smtpConfig = new SmtpConfig("127.0.0.1", 3025);
    }

    @AfterEach
    void tearDown() {
        localSmtpServer.stop();
    }

    @Test
    void oneMail() throws MessagingException {
        SmtpMailSender smtpMailSender = new SmtpMailSender(smtpConfig);

        smtpMailSender.send(MailInfo.greetings("Andrea", "andrea@foobar.com"));

        MailInfo expected = MailInfo.greetings("Andrea", "andrea@foobar.com");
        assertThat(localSmtpServer.receivedMessages()).contains(expected);
    }

    @Test
    void manyMails() throws MessagingException {
        SmtpMailSender smtpMailSender = new SmtpMailSender(smtpConfig);

        smtpMailSender.send(MailInfo.greetings("Andrea", "andrea@foobar.com"));
        smtpMailSender.send(MailInfo.greetings("Mary", "mary@foobar.com"));
        smtpMailSender.send(MailInfo.greetings("John", "john@foobar.com"));

        assertThat(localSmtpServer.receivedMessages()).contains(
                MailInfo.greetings("Andrea", "andrea@foobar.com"),
                MailInfo.greetings("Mary", "mary@foobar.com"),
                MailInfo.greetings("John", "john@foobar.com"));
    }

    @Test
    void smtpDown() {
        SmtpMailSender smtpMailSender = new SmtpMailSender(new SmtpConfig("127.0.0.1", 666));

        assertThatThrownBy(() -> smtpMailSender.send(MailInfo.greetings("Andrea", "andrea@foobar.com")))
            .isInstanceOf(NoDeliveryException.class)
            .hasMessageContaining("andrea@foobar.com");
    }
}
