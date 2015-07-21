package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.Deal;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.service.GoodsException;
import it.sevenbits.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
            mailMessage.setSubject("");
            mailMessage.setText("Уважаемый " + goods.getAuthor() + ". Вашим объявлением: " + goods.getTitle() + " заинтересовались"
                    + " Пройдите по ссылке, чтобы подтвердить" + "\n http://sport-equipment-rent.7bits.it/deal?deal_id=" + deal.getId() + "&accept=true \n"
                    + "Или по этой, чтобы отклонить: " + "\n  http://sport-equipment-rent.7bits.it/deal?deal_id=" + deal.getId() + "&accept=false");
            javaMailSender.send(mailMessage);
        } catch (GoodsException e) {
            e.printStackTrace();
        }

    }
}
