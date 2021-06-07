package it.enel.kata.birthday_greetings;

import java.time.LocalDate;

public class Employee {
    private final String name;
    private final String email;
    private final BirthDate birthDate;

    public Employee(String name, String email, BirthDate birthDate) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isBirthday(LocalDate today) {
        return birthDate.isBirthday(today);
    }
}
