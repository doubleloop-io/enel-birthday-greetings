package it.enel.kata.birthday_greetings;

import java.time.LocalDate;

public class BirthDate {
    public LocalDate date;

    public BirthDate(LocalDate date) {
        this.date = date;
    }

    boolean isBirthday(LocalDate today) {
        return date.getMonth() == today.getMonth() &&
                date.getDayOfMonth() == today.getDayOfMonth();
    }
}
