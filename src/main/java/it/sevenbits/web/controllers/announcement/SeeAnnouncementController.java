package it.sevenbits.web.controllers.announcement;

import it.sevenbits.domain.Deal;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
import it.sevenbits.service.DealService;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.UserService;
import it.sevenbits.service.exceptions.RepeatedDealException;
import it.sevenbits.service.exceptions.ServiceException;
import it.sevenbits.service.exceptions.YourAnnouncementException;
import it.sevenbits.service.validators.TakeGoodsValidator;
import it.sevenbits.web.forms.DateForm;
import it.sevenbits.web.views.GetAnnouncementView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by awemath on 7/27/15.
 */
@Controller
public class SeeAnnouncementController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @Autowired
    private DealService dealService;

    private Logger logger = Logger.getLogger(SeeAnnouncementController.class);





    @RequestMapping(value = "/see_announcement", method = RequestMethod.GET)
    public String announcementPage(
            @RequestParam(value = "announcement_id", required = false) final String announcementId,
            final Model model) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Goods goods = goodsService.getGoods(Long.valueOf(announcementId));
            User landlord = userService.getUser(goods.getAuthorId());
            model.addAttribute("Goods", goods);
            if (name.equals("anonymousUser")) {
                User user = userService.getUser(name);
                model.addAttribute("isAuthor", goodsService.isAuthor(Long.valueOf(announcementId), user));
            } else {
                model.addAttribute("isAuthor", false);
            }


            model.addAttribute("isAuth", name != "anonymousUser");
            model.addAttribute("user", landlord);
        } catch (ServiceException e) {
            logger.error("An error appeared on the getting goods from repository: ", e);
            return "home/error";
        }
        model.addAttribute("date", new DateForm());
        return "home/see_announcement";
    }

    @Autowired
    private TakeGoodsValidator validator;


    @RequestMapping(value = "/getIt", method = RequestMethod.POST)
    public @ResponseBody GetAnnouncementView getIt(
            final HttpServletRequest request) {
        DateForm form = new DateForm();
        form.setFrom((String) request.getParameter("from"));
        form.setTo((String) request.getParameter("to"));
        long announcementId;
        GetAnnouncementView view = new GetAnnouncementView();
        try {
            announcementId = Long.valueOf(request.getParameter("announcementId"));
            final Map<String, String> errors = validator.validate(form, announcementId);

            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            if (errors.isEmpty()) {
                //parse form
                String from = form.getFrom();
                String to = form.getTo();
                Deal deal = new Deal();
                deal.setEstimateStartDate(from);
                deal.setEstimateEndDate(to);
                dealService.submitDeal(deal, String.valueOf(announcementId));
                view.setIsSuccess(true);
            } else {
                //create model with exceptions
                view.setIsSuccess(false);
                view.setErrors(errors);
                view.setFrom(form.getFrom());
                view.setTo(form.getTo());
                view.setIsAuth(name.equals("anonymousUser"));
            }
        } catch (ServiceException e) {
            logger.error("An error appeared on the getting goods from repository: ", e);
            view.addError("Problem", "Sorry, but we have some trouble");
            view.setIsSuccess(false);
        } catch (NumberFormatException e) {
            logger.error("An error occured on the creating a deal: ", e);
            view.setIsSuccess(false);
        } catch (YourAnnouncementException e) {
            view.setIsSuccess(false);
            view.addError("Problem", "It is your announcement");
        } catch (RepeatedDealException e) {
            view.setIsSuccess(false);
            view.addError("Problem", "You already picked it");
        }

        return view;
    }

}
