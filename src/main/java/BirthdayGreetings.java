import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.util.Properties;

public class BirthdayGreetings {
    public void send(LocalDate today) throws MessagingException {
        // - get filepath and date as parameter
        // - pars the file
        // - find employee born on 2021/10/08
        // - send email
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
    }
}
