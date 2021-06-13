package it.enel.kata.birthday_greetings;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        ArrayList<Employee> employees = new ArrayList<>();

        for (String line : lines.stream().skip(1).toArray(String[]::new)) {
            Employee employee = parseEmployeeLine(line);

            if(employee.isBirthday(today)) {
                MailInfo mail = MailInfo.greetings(employee.getName(), employee.getEmail());
                smtpMailSender.sendMail(mail);
            }
        }
    }

    private Employee parseEmployeeLine(String line) {
        String[] employeeParts = Arrays.stream(line.split(","))
                .map(String::trim)
                .toArray(String[]::new);
        return new Employee(employeeParts[1], employeeParts[3], new BirthDate(parseDate(employeeParts[2])));
    }

    private LocalDate parseDate(String date) {
        return LocalDate.parse(
                date,
                DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
