package it.enel.kata.birthday_greetings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CsvEmployeeCatalog {
    public final FileConfig fileConfig;

    public CsvEmployeeCatalog(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }

    public LocalDate parseDate(String date) {
        return LocalDate.parse(
                date,
                DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }
}
