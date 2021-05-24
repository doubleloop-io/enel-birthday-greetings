import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
    void sendMail() throws MessagingException {
        GreenMail greenMail = new GreenMail();
        greenMail.start();

        GreenMailUtil.sendTextEmailTest("john.doe@foobar.com", "no-reply@foobar.com", "Happy birthday!", "Happy birthday, dear John!");

        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertEquals("Happy birthday, dear John!", GreenMailUtil.getBody(receivedMessage));
        assertEquals("Happy birthday!", receivedMessage.getSubject());
        assertEquals("john.doe@foobar.com", receivedMessage.getRecipients(Message.RecipientType.TO)[0].toString());
        assertEquals("no-reply@foobar.com", receivedMessage.getFrom()[0].toString());

        greenMail.stop();
    }
}
