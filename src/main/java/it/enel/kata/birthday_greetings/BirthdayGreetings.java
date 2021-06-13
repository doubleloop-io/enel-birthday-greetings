package it.enel.kata.birthday_greetings;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BirthdayGreetings {

    private final CsvEmployeeCatalog csvEmployeeCatalog;
    private final SmtpMailSender smtpMailSender;

    public BirthdayGreetings(FileConfig fileConfig, SmtpConfig smtpConfig) {
        csvEmployeeCatalog = new CsvEmployeeCatalog(fileConfig);
        smtpMailSender = new SmtpMailSender(smtpConfig);
    }

    public void send(LocalDate today) throws IOException {
        for (Employee employee : loadEmployees()) {
            if(employee.isBirthday(today)) {
                MailInfo mail = MailInfo.greetings(employee.getName(), employee.getEmail());
                smtpMailSender.sendMail(mail);
            }
        }
    }

    private Employee[] loadEmployees() throws IOException {
        List<String> lines = Files.readAllLines(csvEmployeeCatalog.fileConfig.getEmployeesFilePath());
        ArrayList<Employee> employees = new ArrayList<>();

        for (String line : lines.stream().skip(1).toArray(String[]::new)) {
            employees.add(csvEmployeeCatalog.parseEmployeeLine(line));
        }
        return employees.toArray(new Employee[0]);
    }

}
