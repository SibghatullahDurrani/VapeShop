package com.sibghat.vape_shop;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.UnsupportedEncodingException;

@SpringBootTest
public class TestTest {


    private final JavaMailSender mailSender;

    @Autowired
    public TestTest(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void sendVerificationEmail()
            throws MessagingException, UnsupportedEncodingException {

        String toAddress = "to@test.com";
        String fromAddress = "from@test.com";
        String senderName = "test";
        String subject = "Please verify your registration";
        String content = "test";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);

    }

    @Test
    public void test() throws MessagingException, UnsupportedEncodingException {
        sendVerificationEmail();
    }



}
