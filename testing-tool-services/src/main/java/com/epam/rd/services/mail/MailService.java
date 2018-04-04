package com.epam.rd.services.mail;

public interface MailService {

    void sendMailTo(String[] recipients, String subject, String text);

    void sendMailTo(String recipient, String subject, String text);

}
