package it.sevenbits.web.controllers;

import it.sevenbits.domain.Goods;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.UserService;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by awemath on 7/7/15.
 */
@Controller
public class HomeController {

    private static Logger logger = Logger.getLogger(HomeController.class);
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String mainPage(final Model model) {
        try {
            List<Goods> goods = goodsService.findAll();
            for (int i = 0; i < goods.size(); i++) {
                goods.get(i).setAuthorImage(userService.getUser(goods.get(i).getAuthorId()).getImageUrl());
            }
            model.addAttribute("isAuth",
                    !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"));
            model.addAttribute("goods", goods);
        } catch (GoodsException e) {
            logger.error(e.getMessage());
        } catch (ServiceException e) {
            logger.error("An error appeared on getting user", e);
        }

        return "home/index";
    }

}
