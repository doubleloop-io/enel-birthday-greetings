package it.enel.kata.birthday_greetings.acceptance;

import it.enel.kata.birthday_greetings.domain.Employee;
import it.enel.kata.birthday_greetings.domain.EmployeeCatalog;

public class InMemoryEmployeeCatalog implements EmployeeCatalog {
    private final Employee[] employees;

    public InMemoryEmployeeCatalog(Employee... employees) {
        this.employees = employees;
    }

    @Override
    public Employee[] loadAll() {
        return employees;
    }
}
