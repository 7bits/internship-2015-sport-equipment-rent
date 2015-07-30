package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.Deal;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.service.GoodsException;
import it.sevenbits.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
            helper = new MimeMessageHelper(message, true,"UTF-8");
            helper.setTo("dart.fk712@gmail.com");
            /*FileSystemResource file = new FileSystemResource(new File("/home/awemath/workspace/internship-2015-sport-equipment-rent/sport-rent-equipment/" +
                    "src/main/resources/templates/home/letter.html"));
            helper.addInline("identifier1234", file);*/
            String str = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                    "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                    "    <head>\n" +
                    "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                    "        <title>Approve or reject request</title>\n" +
                    "    </head>\n" +
                    "    <body yahoo bgcolor=\"white\" style=\"margin: 0; padding: 0; min-width: 100%!important;\">\n" +
                    "        <!-- <table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "            <tr>\n" +
                    "                <td> -->\n" +
                    "                    <!--[if (gte mso 9)|(IE)]>\n" +
                    "                    <table width=\"600\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                    "                    <tr>\n" +
                    "                    <td>\n" +
                    "                    <![endif]-->\n" +
                    "                    <table class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"width: 100%; max-width: 600px;\">\n" +
                    "                        <tr>\n" +
                    "                            <td>\n" +
                    "                                <table width=\"192\" height=\"70\" align=\"left\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                                    <tr>\n" +
                    "                                        <td width=\"192\" style=\"padding: 10px 0 10px 10px\">\n" +
                    "                                            <a href=\"sport-equipment-rent.7bits.it\">\n" +
                    "                                                <img src=\"http://s014.radikal.ru/i326/1507/dd/0931dbf5a686.png\" width=\"192\" height=\"51\" border=\"0\" alt=\"\" / >\n" +
                    "                                            </a>\n" +
                    "                                        </td>\n" +
                    "                                    </tr>\n" +
                    "                                </table>\n" +
                    "                                <!--[if (gte mso 9)|(IE)]>\n" +
                    "                                <table width=\"383\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                    "                                <tr>\n" +
                    "                                <td>\n" +
                    "                                <![endif]-->\n" +
                    "                                <table class=\"col383\" align=\"right\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; max-width: 383px;\">\n" +
                    "                                        <tr>\n" +
                    "                                            <td>\n" +
                    "                                            </td>\n" +
                    "                                        </tr>\n" +
                    "                                </table>\n" +
                    "                                <!--[if (gte mso 9)|(IE)]>\n" +
                    "                                </td>\n" +
                    "                                </tr>\n" +
                    "                                </table>\n" +
                    "                                <![endif]-->\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                        <tr>\n" +
                    "                            <td>\n" +
                    "                                <table bgcolor=\"white\" style=\"padding: 10px 0 10px 0\" width=\"375\" align=\"left\" height=\"150\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                    "                                    <tr>\n" +
                    "                                        <td style=\"box-shadow: 0 3px 5px rgb(220,220,220);\" bgcolor=\"#019199\">\n" +
                    "                                            <p style=\"color: white; font-size: 1.5em; padding: 0 0 0 0\" align=\"left\">\n" +
                    "                                                ВАШИМ ОБЪЯВЛЕНИЕМ ЗАИНТЕРЕСОВАЛИСЬ\n" +
                    "                                            </p>\n" +
                    "                                        <p style=\"color: white; font-size: 1em; padding: 0 0 0 0\" align=\"left\">\n" +
                    "                                                Пользователь <!--имя пользователя который подал заявку --> намерен взять в аренду <!--наз-е арендуемого товара-->\n" +
                    "                                            </p>\n" +
                    "                                        </td>\n" +
                    "                                    </tr>\n" +
                    "                                </table>\n" +
                    "                                <!--[if (gte mso 9)|(IE)]>\n" +
                    "                                <table width=\"175\" align=\"left\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                    "                                <tr>\n" +
                    "                                <td>\n" +
                    "                                <![endif]-->\n" +
                    "                                <table align=\"center\" border=\"0\" width=\"200\" bgcolor=\"white\" style=\"padding: 10px 0 10px 0\" height=\"150\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%; max-width: 175px\">\n" +
                    "                                    <tr>\n" +
                    "                                        <td>\n" +
                    "                                            <table align=\"center\" border=\"0\" width=\"100%\" height=\"65\" cellpadding=\"0\" cellspacing=\"0\" style=\"padding: 0 5px 5px 0\" style=\"width: 100%; max-width: 175px\">\n" +
                    "                                                <tr>\n" +
                    "                                                    <a href=\"ссылка куда переходить при передаче\" style=\"text-decoration: none;\">\n" +
                    "                                                        <td style=\"box-shadow: 0 3px 5px rgb(220,220,220);\" bgcolor=\"#6ccdd3\" text-align: center;>\n" +
                    "                                                            <p style=\"text-align: center; color: white; font-size: 1.25em; padding: 0 0 0 0;\">\n" +
                    "                                                                Передал\n" +
                    "                                                            </p>\n" +
                    "                                                        </td>\n" +
                    "                                                    </a>\n" +
                    "                                                </tr>\n" +
                    "                                            </table>\n" +
                    "                                            <table align=\"center\" border=\"0\" width=\"100%\" height=\"65\" cellpadding=\"0\" cellspacing=\"0\"  style=\"padding: 5px 5px 0 0\" style=\"width: 100%; max-width: 175px; a: none\">\n" +
                    "                                                <tr>\n" +
                    "                                                    <a href=\"ссылка куда переходить при отклонении\" style=\"text-decoration: none;\">\n" +
                    "                                                        <td style=\"box-shadow: 0 3px 5px rgb(220,220,220);\" bgcolor=\"#d1d1d1\" text-align: center;>\n" +
                    "                                                            <p style=\"text-align: center; color: white; font-size: 1.25em; padding: 0 0 0 0;\">\n" +
                    "                                                                Отклонил\n" +
                    "                                                            </p>\n" +
                    "                                                        </td>\n" +
                    "                                                    </a>\n" +
                    "                                                </tr>\n" +
                    "                                            </table>        \n" +
                    "                                        </td>\n" +
                    "                                    </tr>\n" +
                    "                                </table>\n" +
                    "                                <!--[if (gte mso 9)|(IE)]>\n" +
                    "                                </td>\n" +
                    "                                </tr>\n" +
                    "                                </table>\n" +
                    "                                <![endif]-->\n" +
                    "                            </td>\n" +
                    "                        </tr>\n" +
                    "                    </table>\n" +
                    "                    <!--[if (gte mso 9)|(IE)]>\n" +
                    "                    </td>\n" +
                    "                    </tr>\n" +
                    "                    </table>\n" +
                    "                    <![endif]-->\n" +
                    "                <!-- </td>\n" +
                    "            </tr>\n" +
                    "        </table> -->\n" +
                    "    </body>\n" +
                    "</html>\n" +
                    "\n";
            helper.setText(str, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


// let's include the infamous windows Sample file (this time copied to c:/)


        javaMailSender.send(message);
    }
}
