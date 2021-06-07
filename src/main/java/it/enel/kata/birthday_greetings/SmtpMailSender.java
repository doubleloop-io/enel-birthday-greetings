package it.enel.kata.birthday_greetings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SmtpMailSender {
    private final SmtpConfig smtpConfig;

    public SmtpMailSender(SmtpConfig smtpConfig) {
        this.smtpConfig = smtpConfig;
    }

    void sendMail(MailInfo mail) throws MessagingException {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", smtpConfig.getHost());
        properties.setProperty("mail.smtp.port", Integer.toString(smtpConfig.getPort()));
        Session session = Session.getDefaultInstance(properties);

        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(mail.getFrom()));
        msg.setSubject(mail.getSubject());
        msg.setText(mail.getBody());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo(), false));

        Transport.send(msg);
    }
}
