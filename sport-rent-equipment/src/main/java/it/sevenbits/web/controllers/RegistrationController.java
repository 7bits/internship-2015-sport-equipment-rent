package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.RegistrationForm;
import it.sevenbits.web.service.AddNewGoodsFormValidator;
import it.sevenbits.web.service.GoodsException;
import it.sevenbits.web.service.GoodsService;
import it.sevenbits.web.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by awemath on 7/14/15.
 */

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AddNewGoodsFormValidator validator;
    Logger LOG = Logger.getLogger(RegistrationController.class);

    @RequestMapping(method= RequestMethod.GET)
    public String login(final Model model){
        model.addAttribute("registration", new RegistrationForm());
        return "/home/registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute RegistrationForm form, final Model model){
        try {
            userService.save(form);
        } catch (GoodsException e) {
            LOG.error(e);
        }
        try {
            model.addAttribute("goods", goodsService.findAll());
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        return "/home/index";
    }
}
