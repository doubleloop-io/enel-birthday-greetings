import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class BirthdayGreetings {
    public void send(LocalDate today) throws MessagingException, IOException {
        // - get filepath and date as parameter
        // - pars the file
        // - find employee born on 2021/10/08
        // - send email
        List<String> lines = Files.readAllLines(Path.of("employee.csv"));
        String johnDoe = lines.get(1);
        String[] johnParts = Arrays.stream(johnDoe.split(","))
                .map(String::trim)
                .toArray(String[]::new);
        String employeeName = johnParts[1];
        String employeeEmail = johnParts[3];

        //Get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", "127.0.0.1");
        properties.setProperty("mail.smtp.port", "3025");
        Session session = Session.getDefaultInstance(properties);

        MimeMessage msg = new MimeMessage(session);
        //set message headers
        msg.setFrom(new InternetAddress("no-reply@foobar.com"));
        msg.setSubject("Happy birthday!");
        msg.setText("Happy birthday, dear " + employeeName + "!");
//        msg.setText("Happy birthday, dear John!");
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(employeeEmail, false));

        Transport.send(msg);
    }
}
