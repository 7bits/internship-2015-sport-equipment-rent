package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.Deal;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.User;
import it.sevenbits.web.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by awemath on 7/7/15.
 */
@Controller
public class HomeController {

    private static Logger LOG = Logger.getLogger(HomeController.class);
    @Autowired
    private GoodsService service;


    @Autowired
    private AddNewGoodsFormValidator validator;




    @RequestMapping(value="/", method = RequestMethod.GET)
    public String mainPage(final Model model){
        try {
            model.addAttribute("goods", service.findAll());
            model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        } catch (GoodsException e) {
            e.printStackTrace();
    }

        return "home/index";
    }


    @RequestMapping(value="/see_announcement", method = RequestMethod.GET)
    public String announcementPage(@RequestParam(value="announcement_id", required = false) String announcementId,  final Model model) {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            Goods goods = service.getGoods(Long.valueOf(announcementId));
            User user = userService.getUser(name);
            model.addAttribute("Goods", goods);
            if(name!="anonymousUser") {
                model.addAttribute("isAuthor", user.getId().equals(goods.getAuthorId()));
            }else{
                model.addAttribute("isAuthor", false);
            }
            model.addAttribute("isAuth", name!="anonymousUser");
        } catch (GoodsException e) {
            e.printStackTrace();
        }

        return "home/see_announcement";
    }

    @Autowired
    MailSubmissionController mailSubmissionController;

    @Autowired
    UserService userService;

    @Autowired
    DealService dealService;

    @RequestMapping(value="/getIt", method = RequestMethod.GET)
    public String getIt(@RequestParam(value="announcement_id", required = false) String announcementId, final Model model) {
        try {
            Goods goods = service.getGoods(Long.valueOf(announcementId));
            Deal deal = new Deal(goods.getAuthorId(), userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId(),
                    goods.getId());

            //dealService.save(deal);
           // mailSubmissionController.send(goods, deal);

            if(!dealService.isExist(deal)) {
                dealService.save(deal);
                deal.setId(dealService.getId(deal));
                mailSubmissionController.send(goods, deal);
            }else{
                return "home/error_message";
            }


        } catch (GoodsException e) {
            LOG.error("An error occured on the creating a deal: "+e.getMessage());
        }
        try {
            model.addAttribute("Goods", service.getGoods(Long.valueOf(announcementId)));
            model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser");
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        return "home/application_submitted";
    }


}
