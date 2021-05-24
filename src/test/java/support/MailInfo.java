package support;

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

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
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
}
