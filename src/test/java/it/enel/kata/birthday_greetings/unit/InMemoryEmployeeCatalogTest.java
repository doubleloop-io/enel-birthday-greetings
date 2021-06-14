package it.enel.kata.birthday_greetings.unit;

import it.enel.kata.birthday_greetings.acceptance.InMemoryEmployeeCatalog;
import it.enel.kata.birthday_greetings.contract.EmployeeCatalogContractTest;
import it.enel.kata.birthday_greetings.domain.Employee;
import it.enel.kata.birthday_greetings.domain.EmployeeCatalog;

public class InMemoryEmployeeCatalogTest extends EmployeeCatalogContractTest {
    @Override
    protected EmployeeCatalog employeeCatalogWith(Employee... employees) {
        return new InMemoryEmployeeCatalog(employees);
    }
}
