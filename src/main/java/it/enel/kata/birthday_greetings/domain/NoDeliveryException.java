package it.enel.kata.birthday_greetings.domain;

public class NoDeliveryException extends RuntimeException {
    public NoDeliveryException(String mailTo, Throwable ex) {
        super("Unable to deliver message to " + mailTo, ex);
    }
}
