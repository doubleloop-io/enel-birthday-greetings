package it.enel.kata.birthday_greetings;

import java.time.LocalDate;
import java.time.Month;

public class BirthDate {
    public LocalDate date;

    public BirthDate(LocalDate date) {
        this.date = date;
    }

    boolean isBirthday(LocalDate today) {
        if (date.getDayOfMonth() == 29 && date.getMonth() == Month.FEBRUARY)
            return today.getMonth() == Month.FEBRUARY && today.getDayOfMonth() == 28;
        return date.getMonth() == today.getMonth() &&
                date.getDayOfMonth() == today.getDayOfMonth();
    }
}
