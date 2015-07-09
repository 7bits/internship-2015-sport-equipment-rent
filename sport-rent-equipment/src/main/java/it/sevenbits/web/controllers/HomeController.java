package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.GoodsForm;
import it.sevenbits.web.service.AddNewGoodsFormValidator;
import it.sevenbits.web.service.GoodsException;
import it.sevenbits.web.service.GoodsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

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

    @RequestMapping(value ="/", method = RequestMethod.GET)
    public String index(final Model model){
        model.addAttribute("goods", new GoodsForm());
        return "home/index";
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public String submit(@ModelAttribute GoodsForm form, final Model model){

        final Map<String, String> errors = validator.validate(form);
        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            LOG.info("Subscription form contains errors.");
            return "home/index";
        }

        try {
            service.save(form);
        } catch (GoodsException e) {
           LOG.info(e.getMessage());
        }
        model.addAttribute("goods", form);
        return "home/index";
    }
}
