package it.sevenbits.web.controllers;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.JadeTemplate;
import it.sevenbits.web.domain.Deal;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.User;
import it.sevenbits.web.service.goods.GoodsException;
import it.sevenbits.web.service.goods.GoodsService;
import it.sevenbits.web.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by awemath on 7/17/15.
 */
@RestController
public class MailSubmissionController {


    private final JavaMailSender javaMailSender;

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;


    @Autowired
    private JadeConfiguration jade;

    @Autowired
    MailSubmissionController(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @ResponseStatus(HttpStatus.CREATED)
    public void sendEmail(String html, String title, String to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = null;

            helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setFrom("sportequipmentrent@gmail.com");
            helper.setSubject(title);
            helper.setText(html);
            javaMailSender.send(mailMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    //letter_deny
        @ResponseStatus(HttpStatus.CREATED)
        public void sendDeny (Deal deal){
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            try {
                User landlord = userService.getUser(deal.getLandlordId());
                User renting = userService.getUser(deal.getRentingId());
                Goods goods = goodsService.getGoods(deal.getGoodsId());
                JadeTemplate template = jade.getTemplate("home/letter/letter_deny");
                HashMap<String, Object> model = new HashMap<String, Object>();
                String title = "";
                model.put("confirmLink", "sport-equipment-rent.7bits.it");
                model.put("landlord", landlord);
                model.put("renting", renting);
                model.put("goods", goods);

                String html = jade.renderTemplate(template, model);

                sendEmail(html, title, renting.getEmail());

            } catch (GoodsException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //letter_confirm_start_rent
        @ResponseStatus(HttpStatus.CREATED)
        public void sendConfirmationMail (Deal deal){
            try {
                User landlord = userService.getUser(deal.getLandlordId());
                User renting = userService.getUser(deal.getRentingId());
                Goods goods = goodsService.getGoods(deal.getGoodsId());


                JadeTemplate template = jade.getTemplate("home/letter/letter_confirm_start_rent");
                HashMap<String, Object> model = new HashMap<String, Object>();
                String title = "";
                model.put("denyLink", "sport-equipment-rent.7bits.it");
                model.put("confirmLink", "sport-equipment-rent.7bits.it");
                model.put("landlord", landlord);
                model.put("renting", renting);
                model.put("goods", goods);
                String html = jade.renderTemplate(template, model);
                sendEmail(html, title, renting.getEmail());
            } catch (GoodsException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //letter_end_of_rent
        @ResponseStatus(HttpStatus.CREATED)
        public void sendClose (Deal deal){
            try {
                User landlord = userService.getUser(deal.getLandlordId());
                User renting = userService.getUser(deal.getRentingId());
                Goods goods = goodsService.getGoods(deal.getGoodsId());
                JadeTemplate template = jade.getTemplate("home/letter/letter_confirm_start_rent");
                HashMap<String, Object> model = new HashMap<String, Object>();
                String title = "";
                model.put("confirmLink", "sport-equipment-rent.7bits.it");
                model.put("landlord", landlord);
                model.put("renting", renting);
                model.put("goods", goods);
                String html = jade.renderTemplate(template, model);
                sendEmail(html, title, landlord.getEmail());
            } catch (GoodsException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //letter
        @ResponseStatus(HttpStatus.CREATED)
        public void sendHtmlEmail (Deal deal){
            try {
                User landlord = userService.getUser(deal.getLandlordId());
                User renting = userService.getUser(deal.getRentingId());
                Goods goods = goodsService.getGoods(deal.getGoodsId());
                JadeTemplate template = jade.getTemplate("home/letter/letter_confirm_start_rent");
                HashMap<String, Object> model = new HashMap<String, Object>();
                String title = "";
                model.put("denyLink", "sport-equipment-rent.7bits.it");
                model.put("confirmLink", "sport-equipment-rent.7bits.it");
                model.put("renting", renting);
                model.put("goods", goods);
                String html = jade.renderTemplate(template, model);
                sendEmail(html, title, landlord.getEmail());

            } catch (GoodsException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
