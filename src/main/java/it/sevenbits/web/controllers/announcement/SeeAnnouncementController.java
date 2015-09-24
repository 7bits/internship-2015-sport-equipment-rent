package it.sevenbits.web.controllers.announcement;

import it.sevenbits.web.controllers.MailSubmissionController;
import it.sevenbits.web.forms.DateForm;
import it.sevenbits.domain.Deal;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
import it.sevenbits.service.DealService;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.validators.TakeGoodsValidator;
import it.sevenbits.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by awemath on 7/27/15.
 */
@Controller
public class SeeAnnouncementController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    UserService userService;

    @Autowired
    DealService dealService;

    Logger LOG = Logger.getLogger(SeeAnnouncementController.class);


    @Autowired
    MailSubmissionController mailSubmissionController;


    @RequestMapping(value = "/see_announcement", method = RequestMethod.GET)
    public String announcementPage(@RequestParam(value="announcement_id", required = false) String announcementId,  final Model model) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Goods goods = goodsService.getGoods(Long.valueOf(announcementId));
            User user = userService.getUser(name);
            User landlord = userService.getUser(goods.getAuthorId());
            model.addAttribute("Goods", goods);
            if(name!="anonymousUser") {
                model.addAttribute("isAuthor", user.getId().equals(goods.getAuthorId()));
            }else{
                model.addAttribute("isAuthor", false);
            }
            model.addAttribute("isAuth", name!="anonymousUser");
            model.addAttribute("user", landlord);
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        model.addAttribute("date", new DateForm());
        return "home/see_announcement";
    }

    @Autowired
    TakeGoodsValidator validator;

    @RequestMapping(value="/getIt", method = RequestMethod.POST)
    public String getIt(@RequestParam(value="announcement_id", required = false) String announcementId, final Model model, final DateForm form) {
        try {
            Goods goods = goodsService.getGoods(Long.valueOf(announcementId));
            Deal deal = new Deal(goods.getAuthorId(), userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId(),
                    goods.getId());
            final Map<String, String> errors =validator.validate(form, Long.valueOf(announcementId));
            if(errors.isEmpty()) {
                String from = form.getFrom();
                String to = form.getTo();

                deal.setEstimateStartDate(from);
                deal.setEstimateEndDate(to);

                if (!dealService.isExist(deal)) {
                    dealService.save(deal);
                    mailSubmissionController.sendHtmlEmail(deal);
                } else {
                    return "home/error_message";
                }
            }else{
                String name = SecurityContextHolder.getContext().getAuthentication().getName();
                User user = userService.getUser(name);
                User landlord = userService.getUser(goods.getAuthorId());
                if(user.getEmail().equals(landlord.getEmail())){
                    return "redirect:/";
                }
                model.addAttribute("Goods", goods);
                if(name!="anonymousUser") {
                    model.addAttribute("isAuthor", user.getId().equals(goods.getAuthorId()));
                }else{
                    model.addAttribute("isAuthor", false);
                }
                model.addAttribute("isAuth", name!="anonymousUser");
                model.addAttribute("user", landlord);
                model.addAttribute(goods);
                model.addAttribute("errors", errors);
                model.addAttribute("date", new DateForm());
                return "home/see_announcement";
            }


        } catch (GoodsException e) {
            LOG.error("An error occured on the creating a deal: "+e.getMessage());
        }
        try {
            model.addAttribute("Goods", goodsService.getGoods(Long.valueOf(announcementId)));
            model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser");
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        return "home/application_submitted";
    }

}
