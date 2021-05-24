import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
    void sendMail() {
        GreenMail greenMail = new GreenMail();
        greenMail.start();
        GreenMailUtil.sendTextEmailTest("to@localhost", "from@localhost", "some subject", "some body");
        assertEquals("some body", GreenMailUtil.getBody(greenMail.getReceivedMessages()[0]));
        greenMail.stop();
    }
}
