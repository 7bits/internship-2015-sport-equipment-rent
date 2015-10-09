package it.sevenbits.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by awemath on 10/9/15.
 */
public class MailSenderService implements Runnable {

    private final JavaMailSender javaMailSender;

    private String html, title, to;


    MailSenderService(
            final JavaMailSender javaMailSender,
            final String html,
            final String title,
            final String to) {
        this.html = html;
        this.title = title;
        this.to = to;
        this.javaMailSender = javaMailSender;
    }


    private Logger logger = Logger.getLogger(MailSenderService.class);
    @Override
    public void run() {
        JavaMailSenderImpl sender = (JavaMailSenderImpl) javaMailSender;

        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setContent(html, "text/html; charset=\"UTF-8\"");

            helper.setTo(to);
            helper.setSubject(title);
            helper.setFrom(sender.getUsername());

            sender.send(mimeMessage);
        } catch (MailException e) {
            logger.error("Email didn`t send", e);
        } catch (MessagingException e) {
            logger.error("Email didn`t send", e);
        }
    }
}
