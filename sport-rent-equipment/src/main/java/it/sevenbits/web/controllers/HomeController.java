package it.sevenbits.web.controllers;

import it.sevenbits.web.service.goods.DealService;
import it.sevenbits.web.service.goods.GoodsException;
import it.sevenbits.web.service.goods.GoodsService;
import it.sevenbits.web.service.users.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by awemath on 7/7/15.
 */
@Controller
public class HomeController {

    private static Logger LOG = Logger.getLogger(HomeController.class);
    @Autowired
    private GoodsService service;


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
                mailSubmissionController.sendHtmlEmail(goods);
            }else{
                return "home/error_message";
            }
}
