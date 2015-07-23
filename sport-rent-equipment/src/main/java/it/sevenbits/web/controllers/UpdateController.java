package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.GoodsForm;
import it.sevenbits.web.service.AddNewGoodsFormValidator;
import it.sevenbits.web.service.GoodsException;
import it.sevenbits.web.service.GoodsService;
import it.sevenbits.web.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by awemath on 7/22/15.
 */
@Controller
@RequestMapping(value = "/update")
public class UpdateController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    UserService userService;

    Logger LOG = Logger.getLogger(UpdateController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String update(@RequestParam(value="announcement_id", required = false) String announcementId, final Model model){
        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        String name =  SecurityContextHolder.getContext().getAuthentication().getName();
        Goods goods = null;
        try {
            goods =  goodsService.getGoods(Long.valueOf(announcementId));
        } catch (GoodsException e) {
            LOG.error("An error occured while picking goods from database at UpdateController class: "+e.getMessage());
            return "home/error";
        }
        GoodsForm form = GoodsForm.valueOf(goods);

        model.addAttribute("goods", form);
        return "home/update_announcement";
    }


    @Autowired
    private AddNewGoodsFormValidator validator;

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@RequestParam(value="announcement_id", required = false) long announcementId,
                         final Model model, @ModelAttribute GoodsForm form){
        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        final Map<String, String> errors = validator.validate(form);
        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
            LOG.info("Update form contains errors.");
            return "home/update_announcement";
        }
        form.setId(announcementId);
        try {
            goodsService.update(form);
        } catch (GoodsException e) {
            LOG.error("An error occured while picking goods from database at UpdateController class: "+e.getMessage());
        }
        return "home/see_announcement";
    }

}
