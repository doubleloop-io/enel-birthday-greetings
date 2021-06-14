package it.enel.kata.birthday_greetings.infrastructure;

import it.enel.kata.birthday_greetings.domain.MailInfo;
import it.enel.kata.birthday_greetings.domain.MailSender;
import it.enel.kata.birthday_greetings.domain.NoDeliveryException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SmtpMailSender implements MailSender {
    private final SmtpConfig smtpConfig;

    public SmtpMailSender(SmtpConfig smtpConfig) {
        this.smtpConfig = smtpConfig;
    }

    public void send(MailInfo mail) {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", smtpConfig.host());
        properties.setProperty("mail.smtp.port", Integer.toString(smtpConfig.port()));
        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(mail.from()));
            msg.setSubject(mail.subject());
            msg.setText(mail.body());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.to(), false));

            Transport.send(msg);
        } catch (MessagingException ex) {
            throw new NoDeliveryException(mail.to(), ex);
        }
    }
}
