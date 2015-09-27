package it.sevenbits.web.controllers.announcement;

import it.sevenbits.service.exceptions.DealServiceException;
import it.sevenbits.service.exceptions.UserServiceException;
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
        } catch (UserServiceException e) {
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
            final Map<String, String> errors =validator.validate(form, Long.valueOf(announcementId));
            if(errors.isEmpty()) {
                //parse form
                String from = form.getFrom();
                String to = form.getTo();
                Deal deal = new Deal();
                deal.setEstimateStartDate(from);
                deal.setEstimateEndDate(to);
                dealService.submitDeal(deal, announcementId);
            } else {
                //create model with exceptions
                Goods goods = goodsService.getGoods(Long.valueOf(announcementId));
                model.addAttribute("Goods", goods);
                model.addAttribute("isAuthor", goodsService.isAuthor(Long.valueOf(announcementId)));
                String name = SecurityContextHolder.getContext().getAuthentication().getName();
                User landlord = userService.getUser(goods.getAuthorId());
                model.addAttribute("isAuth", name!="anonymousUser");
                model.addAttribute("user", landlord);
                model.addAttribute("errors", errors);
                model.addAttribute("date", new DateForm());
                return "home/see_announcement";
            }


        } catch (GoodsException e) {
            LOG.error("An error occured on the creating a deal: "+e.getMessage());
        } catch (UserServiceException e) {
            e.printStackTrace();
        } catch (DealServiceException e) {
            e.printStackTrace();
        } catch(NumberFormatException e) {
            return "/";//exception
        }
        return "home/application_submitted";
    }

}
