package it.enel.kata.birthday_greetings;

import java.io.IOException;
import java.time.LocalDate;

public class BirthdayGreetings {
    private final CsvEmployeeCatalog csvEmployeeCatalog;
    private final SmtpMailSender smtpMailSender;

    public BirthdayGreetings(FileConfig fileConfig, SmtpConfig smtpConfig) {
        this(new CsvEmployeeCatalog(fileConfig), new SmtpMailSender(smtpConfig));
    }

    public BirthdayGreetings(CsvEmployeeCatalog csvEmployeeCatalog, SmtpMailSender smtpMailSender) {
        this.csvEmployeeCatalog = csvEmployeeCatalog;
        this.smtpMailSender = smtpMailSender;
    }

    public void send(LocalDate today) throws IOException {
        for (Employee employee : csvEmployeeCatalog.loadEmployees()) {
            if(employee.isBirthday(today)) {
                MailInfo mail = MailInfo.greetings(employee.name(), employee.email());
                smtpMailSender.sendMail(mail);
            }
        }
    }
}
