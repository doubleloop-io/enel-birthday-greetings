package it.enel.kata.birthday_greetings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class BirthdayGreetings {

    private final FileConfig fileConfig;
    private final SmtpConfig smtpConfig;

    public BirthdayGreetings(FileConfig fileConfig, SmtpConfig smtpConfig) {
        this.fileConfig = fileConfig;
        this.smtpConfig = smtpConfig;
    }

    public void send(LocalDate today) throws MessagingException, IOException {
        List<String> lines = Files.readAllLines(fileConfig.getEmployeesFilePath());

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
                properties.setProperty("mail.smtp.host", smtpConfig.getHost());
                properties.setProperty("mail.smtp.port", Integer.toString(smtpConfig.getPort()));
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
