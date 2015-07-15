package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.RegistrationForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by awemath on 7/14/15.
 */

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    @RequestMapping(method= RequestMethod.GET)
    public String login(){
        return "/registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute RegistrationForm form, final Model model){
        return null;
    }
}
