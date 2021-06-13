package it.enel.kata.birthday_greetings;

import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

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
        return !today.isLeapYear() &&
                date.getDayOfMonth() == 29 &&
                date.getMonth() == Month.FEBRUARY &&
                today.getDayOfMonth() == 28 &&
                today.getMonth() == Month.FEBRUARY;
    }

    @Override
    public String toString() {
        return "BirthDate{" +
                "date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BirthDate birthDate = (BirthDate) o;
        return date.equals(birthDate.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
