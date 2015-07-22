package it.sevenbits.web.controllers;

/**
 * Created by awemath on 7/14/15.
 */

import it.sevenbits.web.service.GoodsException;
import it.sevenbits.web.service.GoodsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private static Logger LOG = Logger.getLogger(HomeController.class);

    @Autowired
    private GoodsService service;

    @RequestMapping(method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error, final Model model) {
        if (error != null) {
            final Map<String, String> errors = new HashMap<String, String>();
            errors.put("Invalid username", "неверный email или пароль");
            model.addAttribute("errors", errors);
        }
        return "home/login";

    }
    @RequestMapping(method= RequestMethod.POST)
    public String submit(final Model model){
        try {
            model.addAttribute("goods", service.findAll());
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        return "home/index";
    }




}
