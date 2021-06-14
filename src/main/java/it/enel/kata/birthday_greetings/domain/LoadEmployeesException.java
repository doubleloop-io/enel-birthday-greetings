package it.enel.kata.birthday_greetings.domain;

public class LoadEmployeesException extends RuntimeException{
    public LoadEmployeesException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoadEmployeesException(String message) {
        super(message);
    }
}
