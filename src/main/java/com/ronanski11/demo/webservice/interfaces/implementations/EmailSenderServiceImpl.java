package com.ronanski11.demo.webservice.interfaces.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ronanski11.demo.webservice.interfaces.EmailSenderService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");

            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }
}
