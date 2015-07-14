package it.sevenbits.web.controllers;

/**
 * Created by awemath on 7/14/15.
 */

import it.sevenbits.web.service.GoodsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
    private static Logger LOG = Logger.getLogger(HomeController.class);
    @Autowired
    private GoodsService service;
    @RequestMapping(method= RequestMethod.GET)
    public String login(){
        return "/login";
    }
}
