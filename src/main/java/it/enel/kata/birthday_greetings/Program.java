package it.enel.kata.birthday_greetings;

import javax.mail.MessagingException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;

public class Program {
    public static void main(String[] args) throws MessagingException, IOException {
        FileConfig fileConfig = new FileConfig(Path.of("employees.csv"));
        SmtpConfig smtpConfig = new SmtpConfig("127.0.0.1", 1025);
        BirthdayGreetings birthdayGreetings = new BirthdayGreetings(fileConfig, smtpConfig);

        birthdayGreetings.send(LocalDate.now());
    }
}
