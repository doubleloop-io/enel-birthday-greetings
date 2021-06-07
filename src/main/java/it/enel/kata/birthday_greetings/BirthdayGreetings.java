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
            LocalDate employeeBirthDate = LocalDate.parse(
                    johnParts[2],
                    DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            Employee employee = new Employee(johnParts[1], johnParts[3], new BirthDate(employeeBirthDate));

            if(employee.isBirthday(today)) {
                MailInfo mail = MailInfo.greetings(employee.getName(), employee.getEmail());

                Properties properties = System.getProperties();
                properties.setProperty("mail.smtp.host", smtpConfig.getHost());
                properties.setProperty("mail.smtp.port", Integer.toString(smtpConfig.getPort()));
                Session session = Session.getDefaultInstance(properties);

                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(mail.getFrom()));
                msg.setSubject(mail.getSubject());
                msg.setText(mail.getBody());
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo(), false));

                Transport.send(msg);
            }
        }
    }
}
