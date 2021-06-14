package it.enel.kata.birthday_greetings.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {
    private final String name;
    private final String email;
    private final BirthDate birthDate;

    public Employee(String name, String email, BirthDate birthDate) {
        this.name = name;
        this.email = email;
        this.birthDate = birthDate;
    }

    public String name() {
        return name;
    }

    public String email() {
        return email;
    }

    public boolean isBirthday(LocalDate today) {
        return birthDate.isBirthday(today);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return name.equals(employee.name) && email.equals(employee.email) && birthDate.equals(employee.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, birthDate);
    }
}
