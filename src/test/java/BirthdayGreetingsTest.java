import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import kotlin.NotImplementedError;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BirthdayGreetingsTest {
    @Test
    @Disabled
    void oneGreeting() {
        //Arrange
        /* Write a file with employees
          ```
          last_name, first_name, date_of_birth, email
          Doe, John, 1982/10/08, john.doe@foobar.com
          Ann, Mary, 1975/09/11, mary.ann@foobar.com
          ```
         */

        //Act - Send greetings Today: 2021/10/08
        // - get filepath and date as parameter
        // - pars the file
        // - find employee born on 2021/10/08
        // - send email

        //Assert
        //Check email to John Doe has been sent
    }

    @Test
    void sendMail() throws MessagingException, UnsupportedEncodingException {
        LocalSmtpServer localSmtpServer = new LocalSmtpServer();
        localSmtpServer.start();

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
        assertEquals("no-reply@foobar.com", receivedMessage.getFrom());
        assertEquals("john.doe@foobar.com", receivedMessage.getTo());
        assertEquals("Happy birthday!", receivedMessage.getSubject());
        assertEquals("Happy birthday, dear John!", receivedMessage.getBody());

        localSmtpServer.stop();
    }

    public static class LocalSmtpServer {
        private GreenMail greenMail = new GreenMail();

        public void start() {
            greenMail.start();
        }

        public void stop() {
            greenMail.stop();
        }

        public MailInfo[] receivedMessages() throws MessagingException {
            ArrayList<MailInfo> received = new ArrayList<>();

            for (MimeMessage receivedMessage : greenMail.getReceivedMessages()) {
                received.add( new MailInfo(
                        receivedMessage.getFrom()[0].toString(),
                        receivedMessage.getRecipients(Message.RecipientType.TO)[0].toString(),
                        receivedMessage.getSubject(),
                        GreenMailUtil.getBody(receivedMessage)));
            }
            return received.toArray(new MailInfo[0]);
        }
    }

    public static class MailInfo {
        private final String from;
        private final String to;
        private final String subject;
        private final String body;

        public MailInfo(String from, String to, String subject, String body) {
            this.from = from;
            this.to = to;
            this.subject = subject;
            this.body = body;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public String getSubject() {
            return subject;
        }

        public String getBody() {
            return body;
        }
    }
}
