package it.sevenbits.web.controllers.announcement;

import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.GoodsForm;
import it.sevenbits.web.service.goods.AddNewGoodsFormValidator;
import it.sevenbits.web.service.goods.GoodsException;
import it.sevenbits.web.service.goods.GoodsService;
import it.sevenbits.web.service.users.UserService;
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

    @Autowired
    UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public String confirm(final Model model, HttpSession session){
        GoodsForm form = (GoodsForm) session.getAttribute("addNewGoods");
        session.removeAttribute("addNewGoods");

        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        if(form==null) {
            return "redirect:/";
        }else{
            session.setAttribute("firstImage", form.getFirstImageUrl());
            session.setAttribute("secondImage", form.getSecondImageUrl());
            session.setAttribute("thirdImage", form.getThirdImageUrl());
            model.addAttribute("goods", form);
        }
        return "home/confirm_announcement";
    }
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute GoodsForm form, final Model model, HttpSession session) {
        final Map<String, String> errors = validator.validate(form);

        form.setFirstImageUrl((String) session.getAttribute("firstImage"));
        form.setSecondImageUrl((String) session.getAttribute("secondImage"));
        form.setThirdImageUrl((String) session.getAttribute("thirdImage"));

        boolean isAuth = SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser";
        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth", isAuth);
            LOG.info("Adding form contains errors.");
            return "home/confirm_announcement";
        }


        Goods goods = null;
        try {
            goods = form.toGoods(userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()));
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        try {
            service.save(goods);
        } catch (GoodsException e) {
            LOG.info(e.getMessage());
        }

        if(form.getFirstImageUrl()!=null)
            service.addImage(goods.getId(), form.getFirstImageUrl());
        if(form.getSecondImageUrl()!=null)
            service.addImage(goods.getId(), form.getSecondImageUrl());
        if(form.getThirdImageUrl()!=null)
            service.addImage(goods.getId(), form.getThirdImageUrl());

        return "redirect:/see_announcement?announcement_id="+goods.getId();
    }
}
