package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.Deal;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.service.GoodsException;
import it.sevenbits.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by awemath on 7/17/15.
 */
@RestController
class MailSubmissionController {


    private final JavaMailSender javaMailSender;

    @Autowired
    UserService userService;

    @Autowired
    MailSubmissionController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @ResponseStatus(HttpStatus.CREATED)
    void send(Goods goods, Deal deal) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        try {
            mailMessage.setTo(userService.getUser(goods.getAuthorId()).getEmail());
            mailMessage.setFrom("sportequipmentrent@gmail.com");
            mailMessage.setSubject("Your announcement on sportequipmentrent");
            mailMessage.setText("<>");
            javaMailSender.send(mailMessage);
        } catch (GoodsException e) {
            e.printStackTrace();
        }

    }

    @ResponseStatus(HttpStatus.CREATED)
    void sendHtmlEmail(Goods goods){
        MimeMessage message = javaMailSender.createMimeMessage();

// use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(userService.getUser(goods.getAuthorId()).getEmail());
            FileSystemResource file = new FileSystemResource(new File("/home/awemath/workspace/internship-2015-sport-equipment-rent/sport-rent-equipment/" +
                    "src/main/resources/templates/home/letter.html"));
            helper.addInline("identifier1234", file);
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (GoodsException e) {
            e.printStackTrace();
        }


// let's include the infamous windows Sample file (this time copied to c:/)


        javaMailSender.send(message);
    }
}
