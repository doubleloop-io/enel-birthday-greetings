package it.enel.kata.birthday_greetings;

public class SmtpConfig {
    private final String host;
    private final int port;

    public SmtpConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String host() {
        return host;
    }

    public int port() {
        return port;
    }
}
