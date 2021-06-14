package it.enel.kata.birthday_greetings.infrastructure;

import it.enel.kata.birthday_greetings.domain.Employee;
import it.enel.kata.birthday_greetings.domain.EmployeeCatalog;
import it.enel.kata.birthday_greetings.domain.MailInfo;

import java.time.LocalDate;

public class BirthdayGreetings {
    private final EmployeeCatalog employeeCatalog;
    private final SmtpMailSender smtpMailSender;

    public BirthdayGreetings(EmployeeCatalog employeeCatalog, SmtpMailSender smtpMailSender) {
        this.employeeCatalog = employeeCatalog;
        this.smtpMailSender = smtpMailSender;
    }

    public void send(LocalDate today) {
        for (Employee employee : employeeCatalog.loadAll()) {
            if(employee.isBirthday(today)) {
                MailInfo mail = MailInfo.greetings(employee.name(), employee.email());
                smtpMailSender.sendMail(mail);
            }
        }
    }
}
