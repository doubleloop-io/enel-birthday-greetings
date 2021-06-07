package it.enel.kata.birthday_greetings;

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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class BirthdayGreetings {
    public void send(LocalDate today) throws MessagingException, IOException {
        // - get filepathas parameter
        List<String> lines = Files.readAllLines(Path.of("employee.csv"));

        for (String line : lines.stream().skip(1).toArray(String[]::new)) {
            String[] johnParts = Arrays.stream(line.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);
            String employeeName = johnParts[1];
            String employeeEmail = johnParts[3];
            LocalDate employeeBirthDate = LocalDate.parse(
                    johnParts[2],
                    DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            if(employeeBirthDate.getMonth() == today.getMonth() &&
                    employeeBirthDate.getDayOfMonth() == today.getDayOfMonth()) {
                Properties properties = System.getProperties();
                properties.setProperty("mail.smtp.host", "127.0.0.1");
                properties.setProperty("mail.smtp.port", "3025");
                Session session = Session.getDefaultInstance(properties);

                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress("no-reply@foobar.com"));
                msg.setSubject("Happy birthday!");
                msg.setText("Happy birthday, dear " + employeeName + "!");
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(employeeEmail, false));

                Transport.send(msg);
            }
        }
    }
}
