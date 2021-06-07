package it.enel.kata.birthday_greetings;

public class NoDeliveryException extends RuntimeException {
    public NoDeliveryException(String mailTo, Throwable ex) {
        super("Unable to deliver message to " + mailTo, ex);
    }
}
