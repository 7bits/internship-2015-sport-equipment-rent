package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.GoodsForm;
import it.sevenbits.web.service.AddNewGoodsFormValidator;
import it.sevenbits.web.service.GoodsException;
import it.sevenbits.web.service.GoodsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by awemath on 7/23/15.
 */
@RequestMapping(value = "/add_announcement")
public class AddAnnouncementController {
    @Autowired
    AddNewGoodsFormValidator validator;

    @Autowired
    GoodsService service;

    Logger LOG=Logger.getLogger(AddAnnouncementController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String index(final Model model) {
        // В модель добавим новый объект формы подписки
        model.addAttribute("goods", new GoodsForm());
        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        // Так как нет аннотации @ResponseBody, то spring будет искать шаблон по адресу home/index
        // Если шаблона не будет найдено, то вернется 404 ошибка
        return "home/add_announcement";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute GoodsForm form, final Model model){
        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        final Map<String, String> errors = validator.validate(form);
        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
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

}
