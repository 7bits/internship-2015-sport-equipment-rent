package it.sevenbits.web.controllers.announcement;


import it.sevenbits.service.exceptions.*;

import it.sevenbits.service.MailSubmissionService;
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
    private MailSubmissionService mail;

    @RequestMapping(value = "/handed", method = RequestMethod.GET)
    public String deal(@RequestParam(value = "deal_id", required = false) final String dealIdVal,
                       @RequestParam(value = "accept", required = false) final String isHandedVal,
                       final Model model) {
        int dealId;
        boolean isHanded;
        try {
            dealId = Integer.valueOf(dealIdVal);
            isHanded = Boolean.valueOf(isHandedVal);
            User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
            dealService.handed(dealId, isHanded, user);
        } catch (ServiceException e) {
            return "home/error";
        } catch (AccessDeniedException e) {
            return "home/error";
        } catch (AlreadyAnsweredException e) {
            return "home/error";
        } catch (NumberFormatException e) {
            return "home/error";
        }
        if (isHanded) {
            return "home/confirm_deal";

        } else {

            return "home/application_is_rejected";
        }


    }


    @RequestMapping(value = "/accept", method = RequestMethod.GET)
    public String accept(@RequestParam(value = "deal_id", required = false) final String dealIdVal,
                         @RequestParam(value = "accept", required = false) final String isGetVal) {

        int dealId;
        boolean isGet;
        try {
            dealId = Integer.valueOf(dealIdVal);
            isGet = Boolean.valueOf(isGetVal);
            dealService.accept(dealId, isGet);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return "home/error";
        } catch (AccessDeniedException e) {
            logger.error(e.getMessage());
            return "home/error";
        } catch (NumberFormatException e) {
            return "home/error";
        }
        if (isGet) {
            return "home/confirm_get";
        } else {
            return "home/message_deny_in_rent";
        }

    }

    @RequestMapping(value = "/close", method = RequestMethod.GET)
    public String closeFirstStep(@RequestParam(value = "deal_id", required = false) final String dealIdVal,
                                 final Model model) {
        Deal deal = null;
        int dealId;

        try {
            dealId = Integer.valueOf(dealIdVal);
            dealService.close(dealId);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
            return "home/error";
        } catch (AccessDeniedException e) {
            return "home/error";
        } catch (NumberFormatException e) {
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
    public String closeEnd(@RequestParam(value = "deal_id", required = false) final String dealIdVal,
                           final Model model) {
        User landlord = null;
        Deal deal = null;
        int dealId;
        try {
            dealId = Integer.valueOf(dealIdVal);
            deal = dealService.getDeal(dealId);
        } catch (ServiceException e) {
            logger.error("An error appeared on getting deal");
            return "home/error";
        }
        try {
            landlord = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (ServiceException e) {
            logger.error("An error appeared on getting user");
            return "home/error";
        }
        try {
            dealService.updateRealEndDate(dealId);
        } catch (ServiceException e) {
            logger.error("An error appeared on updating deal`s real end date");
            return "home/error";
        }
        deal.setIsClosed(true);
        if (landlord.getId() != deal.getLandlordId()) {
            return "home/error_message";
        }
        try {
            dealService.update(deal);
        } catch (ServiceException e) {
            logger.error("An error appeared on updating deal", e);
            return "home/error";
        }
        return "home/message_when_rent_is_end";
    }


}
