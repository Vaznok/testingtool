package com.epam.rd.services.mail.impl;

import com.epam.rd.services.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SimpleMailMessage message;

    @Override
    public void sendMailTo(String[] recipients, String subject, String text) {
        try {
            mailSender.send(createMessage(recipients, subject, text));
        } catch (MailException me) {
            throw new RuntimeException(me);
        }
    }

    @Override
    public void sendMailTo(String recipient, String subject, String text) {
        sendMailTo(new String[] { recipient }, subject, text);
    }

    private SimpleMailMessage createMessage(String[] recipients, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage(message);
        String from = "dinner.breakfastt@gmail.com";
        msg.setFrom(from);
        msg.setTo(recipients);
        msg.setSubject(subject);
        msg.setText(text);
        return msg;
    }
}
