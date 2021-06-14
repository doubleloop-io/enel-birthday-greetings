package it.enel.kata.birthday_greetings.domain;

import java.util.Objects;

public class MailInfo {
    private final String from;
    private final String to;
    private final String subject;
    private final String body;

    public MailInfo(String from, String to, String subject, String body) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.body = body;
    }

    public static MailInfo greetings(String name, String to) {
        return new MailInfo("no-reply@foobar.com", to, "Happy birthday!", "Happy birthday, dear " + name + "!");
    }

    public String from() {
        return from;
    }

    public String to() {
        return to;
    }

    public String subject() {
        return subject;
    }

    public String body() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MailInfo mailInfo = (MailInfo) o;
        return from.equals(mailInfo.from) && to.equals(mailInfo.to) && subject.equals(mailInfo.subject) && body.equals(mailInfo.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, subject, body);
    }

    @Override
    public String toString() {
        return "MailInfo{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
