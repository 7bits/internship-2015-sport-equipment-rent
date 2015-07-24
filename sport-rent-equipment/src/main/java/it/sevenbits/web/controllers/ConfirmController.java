package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.GoodsForm;
import it.sevenbits.web.service.AddNewGoodsFormValidator;
import it.sevenbits.web.service.GoodsException;
import it.sevenbits.web.service.GoodsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by awemath on 7/23/15.
 */
@Controller
@RequestMapping(value = "/confirm")
public class ConfirmController {

    Logger LOG = Logger.getLogger(ConfirmController.class);

    @Autowired
    GoodsService service;

    @Autowired
    AddNewGoodsFormValidator validator;


    @RequestMapping(method = RequestMethod.GET)
    public String confirm(final Model model, HttpSession session){
        GoodsForm form = (GoodsForm) session.getAttribute("addNewGoods");
        if(form==null) {
            return "redirect:/";
        }else{
            model.addAttribute("goods", form);
        }
        return "home/confirm_announcement";
    }
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute GoodsForm form, final Model model, HttpSession session) {
        final Map<String, String> errors = validator.validate(form);
        boolean isAuth = SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser";
        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth", isAuth);
            LOG.info("Adding form contains errors.");
            return "home/confirm_announcement";
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
        return "redirect:/";
    }
}
