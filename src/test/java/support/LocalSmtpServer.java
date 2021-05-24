package support;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;

public class LocalSmtpServer {
    private GreenMail greenMail = new GreenMail();

    public void start() {
        greenMail.start();
    }

    public void stop() {
        greenMail.stop();
    }

    public MailInfo[] receivedMessages() throws MessagingException {
        ArrayList<MailInfo> received = new ArrayList<>();

        for (MimeMessage receivedMessage : greenMail.getReceivedMessages()) {
            received.add(new MailInfo(
                    receivedMessage.getFrom()[0].toString(),
                    receivedMessage.getRecipients(Message.RecipientType.TO)[0].toString(),
                    receivedMessage.getSubject(),
                    GreenMailUtil.getBody(receivedMessage)));
        }
        return received.toArray(new MailInfo[0]);
    }
}
