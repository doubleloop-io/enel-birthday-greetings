package it.enel.kata.birthday_greetings;

public class LoadEmployeesException extends RuntimeException{
    public LoadEmployeesException(String message, Throwable cause) {
        super(message, cause);
    }
}
