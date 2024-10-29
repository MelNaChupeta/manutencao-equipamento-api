package br.com.tads.manutencao_equipamento_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("equipamentosmatheus@gmail.com");

        mailSender.send(message);
    }

    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        helper.setFrom("equipamentosmatheus@gmail.com");

        mailSender.send(message);
    }

}
