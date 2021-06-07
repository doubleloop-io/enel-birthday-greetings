package it.enel.kata.birthday_greetings;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class BirthdayGreetings {

    private final FileConfig fileConfig;
    private final SmtpMailSender smtpMailSender;

    public BirthdayGreetings(FileConfig fileConfig, SmtpConfig smtpConfig) {
        this.fileConfig = fileConfig;
        smtpMailSender = new SmtpMailSender(smtpConfig);
    }

    public void send(LocalDate today) throws IOException {
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
                smtpMailSender.sendMail(mail);
            }
        }
    }
}
