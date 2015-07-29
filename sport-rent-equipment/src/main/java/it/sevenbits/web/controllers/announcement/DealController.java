package it.sevenbits.web.controllers.announcement;

import it.sevenbits.web.controllers.MailSubmissionController;
import it.sevenbits.web.domain.Deal;
import it.sevenbits.web.domain.User;
import it.sevenbits.web.service.goods.DealService;
import it.sevenbits.web.service.goods.GoodsException;
import it.sevenbits.web.service.users.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by awemath on 7/21/15.
 */
@Controller
@RequestMapping(value = "/deal")
public class DealController {
    @Autowired
    DealService dealService;

    @Autowired
    UserService userService;

    Logger LOG = Logger.getLogger(DealController.class);


    @Autowired
    MailSubmissionController mail;

    @RequestMapping(value = "/handed", method = RequestMethod.GET)
    public String deal(@RequestParam(value="deal_id", required = false) long dealId,
                       @RequestParam(value="accept", required = false) boolean isHanded,
                       final Model model) {
        Deal deal = dealService.getDeal(dealId);
        User landlord = null;
        try {
            landlord = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (GoodsException e) {
            LOG.error("An errror occured on getting landlord from the database: "+e.getMessage());
            return "home/error_message";
        }
        if(deal.getLandlordId() != landlord.getId()){
            return "home/error_message";
        }
        if(deal.isAnswered()){
            return "home/error_message";
        } else {
            deal.setIsHanded(isHanded);
            deal.setIsAnswered(true);
            dealService.update(deal);
            if(isHanded) {
                mail.sendHandleOk(deal);
                return "home/confirm_deal";
            }else{
                return "home/application_is_rejected";
            }
        }

    }


    @RequestMapping(value = "/give", method = RequestMethod.GET)
    public String giveGoods(@RequestParam(value="deal_id", required = false) long dealId){
        Deal deal = dealService.getDeal(dealId);
        mail.sendConfirmationMail(deal);

        return ""; //all is good
    }

    @RequestMapping(value="/accept", method = RequestMethod.GET)
    public String accept(@RequestParam(value="deal_id", required = false) long dealId){
        Deal deal = dealService.getDeal(dealId);
        try {
            mail.sendClose(deal);
            dealService.updateRealStartDate(dealId);
        }catch (Exception e){
            LOG.error("An error occured on accepting deal: "+e.getMessage());
        }
        return ""; //start of the using
    }

    @RequestMapping(value = "/close", method=RequestMethod.GET)
    public String close(@RequestParam(value="deal_id", required = false) long dealId){
        Deal deal = dealService.getDeal(dealId);
        dealService.updateRealEndDate(dealId);
        return "redirect:/";
    }

}
