package it.enel.kata.birthday_greetings;

import java.time.LocalDate;
import java.time.Month;

public class BirthDate {
    public LocalDate date;

    public BirthDate(LocalDate date) {
        this.date = date;
    }

    boolean isBirthday(LocalDate today) {
        return isTypicalBirthday(today) || isBirthdayOn29thFeb(today);
    }

    private boolean isTypicalBirthday(LocalDate today) {
        return date.getMonth() == today.getMonth() &&
                date.getDayOfMonth() == today.getDayOfMonth();
    }

    private boolean isBirthdayOn29thFeb(LocalDate today) {
        return date.getDayOfMonth() == 29 && date.getMonth() == Month.FEBRUARY &&
                today.getMonth() == Month.FEBRUARY && today.getDayOfMonth() == 28;
    }
}
