package it.enel.kata.birthday_greetings.contract;

import it.enel.kata.birthday_greetings.domain.BirthDate;
import it.enel.kata.birthday_greetings.domain.Employee;
import it.enel.kata.birthday_greetings.domain.EmployeeCatalog;
import it.enel.kata.birthday_greetings.domain.LoadEmployeesException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public abstract class EmployeeCatalogContractTest {
    protected abstract EmployeeCatalog employeeCatalogWith(Employee... employees);

    @Test
    void oneEmployee() {
        EmployeeCatalog employeeCatalog = employeeCatalogWith(
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8))
        );

        Employee[] employees = employeeCatalog.loadAll();

        assertThat(employees).contains(
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)));
    }

    @Test
    void manyEmployees() {
        EmployeeCatalog employeeCatalog = employeeCatalogWith(
                new Employee("Andrea", "andrea.vallotti@foobar.com", BirthDate.of(1977, 12, 27)),
                new Employee("Carlo", "carlo.didomenico@foobar.com", BirthDate.of(1982, 6, 7)),
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8))
        );

        Employee[] employees = employeeCatalog.loadAll();

        assertThat(employees).contains(
                new Employee("Andrea", "andrea.vallotti@foobar.com", BirthDate.of(1977, 12, 27)),
                new Employee("Carlo", "carlo.didomenico@foobar.com", BirthDate.of(1982, 6, 7)),
                new Employee("John", "john.doe@foobar.com", BirthDate.of(1982, 10, 8)));
    }

    @Test
    void noEmployees() {
        EmployeeCatalog employeeCatalog = employeeCatalogWith();

        assertThatThrownBy(() -> employeeCatalog.loadAll())
                .isInstanceOf(LoadEmployeesException.class);
    }
}
