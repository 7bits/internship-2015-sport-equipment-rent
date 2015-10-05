package it.sevenbits.web.controllers.announcement;


import it.sevenbits.service.exceptions.DealServiceException;

import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.web.controllers.MailSubmissionController;
import it.sevenbits.domain.Deal;
import it.sevenbits.domain.User;
import it.sevenbits.service.DealService;
import it.sevenbits.service.UserService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
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
public class DealController {
    @Autowired
    private DealService dealService;

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(DealController.class);


    @Autowired
    private MailSubmissionController mail;

    @RequestMapping(value = "/handed", method = RequestMethod.GET)
    public String deal(@RequestParam(value = "deal_id", required = false) final long dealId,
                       @RequestParam(value = "accept", required = false) final boolean isHanded,
                       final Model model) {
        try {
            dealService.handed(dealId, isHanded);
        } catch (DealServiceException e) {
            return "home/error";
        }
        if (isHanded) {
            return "home/confirm_deal";

        } else {

            return "home/application_is_rejected";
        }


    }


    @RequestMapping(value = "/accept", method = RequestMethod.GET)
    public String accept(@RequestParam(value = "deal_id", required = false) final long dealId,
                         @RequestParam(value = "accept", required = false) final boolean isGet) {

        try {
            dealService.accept(dealId, isGet);
        } catch (DealServiceException e) {
            logger.error(e.getMessage());
            return "home/error";
        }
        if (isGet) {
            return "home/confirm_get";
        } else {
            return "home/message_deny_in_rent";
        }

    }

    @RequestMapping(value = "/close", method = RequestMethod.GET)
    public String closeFirstStep(@RequestParam(value = "deal_id", required = false) final long dealId,
                                 final Model model) {
        Deal deal = null;
        try {
            dealService.close(dealId);
        } catch (DealServiceException e) {
            return "home/error";

        }
        model.addAttribute("id", deal.getId());
        if (DateTime.parse(deal.getEstimateEndDate()).getMillis() > DateTime.now().getMillis()) {
            return "home/message_when_rent_not_end";
        } else {
            return "redirect:/finally_close?deal_id=" + deal.getId();
        }


    }

    @RequestMapping(value = "/finally_close", method = RequestMethod.GET)
    public String closeEnd(@RequestParam(value = "deal_id", required = false) final long dealId,
                           final Model model) {
        User landlord = null;
        Deal deal = null;
        try {
            deal = dealService.getDeal(dealId);
        } catch (DealServiceException e) {
            logger.error("An error appeared on getting deal");
            return "home/error";
        }
        try {
            landlord = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
        try {
            dealService.updateRealEndDate(dealId);
        } catch (DealServiceException e) {
            return "home/error";
        }
        deal.setIsClosed(true);
        if (landlord.getId() != deal.getLandlordId()) {
            return "home/error_message";
        }
        try {
            dealService.update(deal);
        } catch (DealServiceException e) {
            return "home/error";
        }
        return "home/message_when_rent_is_end";
    }


}
