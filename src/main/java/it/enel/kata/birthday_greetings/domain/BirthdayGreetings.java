package it.enel.kata.birthday_greetings.domain;

import java.time.LocalDate;

public class BirthdayGreetings {
    private final EmployeeCatalog employeeCatalog;
    private final MailSender mailSender;

    public BirthdayGreetings(EmployeeCatalog employeeCatalog, MailSender mailSender) {
        this.employeeCatalog = employeeCatalog;
        this.mailSender = mailSender;
    }

    public void send(LocalDate today) {
        for (Employee employee : employeeCatalog.loadAll()) {
            if(employee.isBirthday(today)) {
                MailInfo mail = MailInfo.greetings(employee.name(), employee.email());
                mailSender.send(mail);
            }
        }
    }
}
