package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.RegistrationForm;
import org.apache.log4j.Logger;
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

    Logger LOG = Logger.getLogger(RegistrationController.class);

    @RequestMapping(method= RequestMethod.GET)
    public String login(){
        return "/home/registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute RegistrationForm form, final Model model){

        final Map<String, String> errors = validator.validate(form);
        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            LOG.info("Subscription form contains errors.");
            return "home/add_announcement";
        }

        try {
            service.save(form);
        } catch (GoodsException e) {
            LOG.info(e.getMessage());
        }
        try {
            model.addAttribute("goods", service.findAll());
        } catch (GoodsException e) {
            LOG.error("Error at the picking goods");
        }
        return "/home/index";
    }
}
