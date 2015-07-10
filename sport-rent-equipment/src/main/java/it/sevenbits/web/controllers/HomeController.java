package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.Goods;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
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


    @RequestMapping(value = "/add_announcement", method = RequestMethod.GET)
    public String index(final Model model) {
        // В модель добавим новый объект формы подписки
        model.addAttribute("goods", new GoodsForm());
        // Так как нет аннотации @ResponseBody, то spring будет искать шаблон по адресу home/index
        // Если шаблона не будет найдено, то вернется 404 ошибка
        return "home/add_announcement";
    }


    @RequestMapping(value="/add_announcement", method = RequestMethod.POST)
    public String submit(@ModelAttribute GoodsForm form, final Model model){

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
        return "home/index";
    }


    @RequestMapping(value="/", method = RequestMethod.GET)
    public String mainPage(final Model model){
        try {
            model.addAttribute("goods", service.findAll());
        } catch (GoodsException e) {
            e.printStackTrace();
    }

        return "/home/index";
    }


}
