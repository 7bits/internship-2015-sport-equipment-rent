package it.sevenbits.service;

import de.neuland.jade4j.JadeConfiguration;
import de.neuland.jade4j.template.JadeTemplate;
import it.sevenbits.domain.Deal;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
import it.sevenbits.service.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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
public class MailSubmissionService {
    private Logger logger = Logger.getLogger(MailSubmissionService.class);
    private final JavaMailSender javaMailSender;
    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;


    @Autowired
    private JadeConfiguration jade;

    @Autowired
    MailSubmissionService(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }



    @ResponseStatus(HttpStatus.CREATED)
    public void sendEmail(final String html,
                          final String title,
                          final String to) {
        MailSenderService mailSenderService = new MailSenderService(javaMailSender, html, title, to);
        Thread thread = new Thread(mailSenderService);
        thread.run();
    }

    //letter_deny
    @ResponseStatus(HttpStatus.CREATED)
    public void sendDeny(final Deal deal) {
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
            Thread thread = new Thread();

            sendEmail(html, title, renting.getEmail());

        } catch (IOException e) {
            logger.error("Email didn`t send", e);
        } catch (ServiceException e) {
            logger.error("Email didn`t send", e);
        }
    }

    //letter_confirm_start_rent
    @ResponseStatus(HttpStatus.CREATED)
    public void sendConfirmationMail(final Deal deal) {
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
        } catch (ServiceException e) {
            logger.error("Email didn`t send", e);
        } catch (IOException e) {
            logger.error("Email didn`t send", e);
        }
    }

    //letter_end_of_rent
    @ResponseStatus(HttpStatus.CREATED)
    public void sendClose(final Deal deal) {
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
        } catch (ServiceException e) {
            logger.error("Email didn`t send", e);
        } catch (IOException e) {
            logger.error("Email didn`t send", e);
        }
    }

    //letter
    @ResponseStatus(HttpStatus.CREATED)
    public void sendHtmlEmail(final Deal deal) {
        try {
            User landlord = userService.getUser(deal.getLandlordId());
            User renting = userService.getUser(deal.getRentingId());
            Goods goods = goodsService.getGoods(deal.getGoodsId());
            JadeTemplate template = jade.getTemplate("home/letter/letter");
            HashMap<String, Object> model = new HashMap<String, Object>();
            String title = "";
            model.put("denyLink", "sport-equipment-rent.7bits.it");
            model.put("confirmLink", "sport-equipment-rent.7bits.it");
            model.put("renting", renting);
            model.put("goods", goods);
            String html = jade.renderTemplate(template, model);
            sendEmail(html, title, landlord.getEmail());

        } catch (IOException e) {
            logger.error("Email didn`t send", e);
        } catch (ServiceException e) {
            logger.error("Email didn`t send", e);
        }
    }
}
