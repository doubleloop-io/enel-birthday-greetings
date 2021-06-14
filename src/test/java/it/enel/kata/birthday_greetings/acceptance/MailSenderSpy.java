package it.enel.kata.birthday_greetings.acceptance;

import it.enel.kata.birthday_greetings.domain.MailInfo;
import it.enel.kata.birthday_greetings.domain.MailSender;

import java.util.ArrayList;

public class MailSenderSpy implements MailSender {
    private final ArrayList<MailInfo> receivedMails = new ArrayList<>();

    @Override
    public void send(MailInfo mail) {
        receivedMails.add(mail);
    }

    public ArrayList<MailInfo> receivedMails() {
        return receivedMails;
    }
}
