package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.Goods;
import it.sevenbits.service.DealService;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.UserService;
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

    private static Logger LOG = Logger.getLogger(HomeController.class);
    @Autowired
    private GoodsService service;


    @RequestMapping(value="/", method = RequestMethod.GET)
    public String mainPage(final Model model){
        try {
            List<Goods> goods = service.findAll();
            for(int i=0;i<goods.size();i++){
                goods.get(i).setAuthorImage(userService.getUser(goods.get(i).getAuthorId()).getImageUrl());
            }
            model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
            model.addAttribute("goods", goods);
        } catch (GoodsException e) {
            e.printStackTrace();
        }

        return "home/index";
    }





    @Autowired
    UserService userService;

    @Autowired
    DealService dealService;
}
