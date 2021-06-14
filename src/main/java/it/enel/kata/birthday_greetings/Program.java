package it.enel.kata.birthday_greetings;

import it.enel.kata.birthday_greetings.domain.BirthdayGreetings;
import it.enel.kata.birthday_greetings.infrastructure.CsvEmployeeCatalog;
import it.enel.kata.birthday_greetings.infrastructure.FileConfig;
import it.enel.kata.birthday_greetings.infrastructure.SmtpConfig;
import it.enel.kata.birthday_greetings.infrastructure.SmtpMailSender;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public class Program {
    public static void main(String[] args) throws IOException {
        CsvEmployeeCatalog csvEmployeeCatalog = new CsvEmployeeCatalog(new FileConfig(Path.of("employees.csv")));
        SmtpMailSender smtpMailSender = new SmtpMailSender(new SmtpConfig("127.0.0.1", 1025));
        BirthdayGreetings birthdayGreetings = new BirthdayGreetings(csvEmployeeCatalog, smtpMailSender);

        birthdayGreetings.send(LocalDate.now());
    }
}
