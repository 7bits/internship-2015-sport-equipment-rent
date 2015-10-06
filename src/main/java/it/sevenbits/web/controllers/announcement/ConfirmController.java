package it.sevenbits.web.controllers.announcement;

import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.UserService;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.exceptions.ServiceException;
import it.sevenbits.web.forms.GoodsForm;
import it.sevenbits.web.validators.AddNewGoodsFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by awemath on 7/23/15.
 */
@Controller
@RequestMapping(value = "/confirm")
public class ConfirmController {

    private Logger logger = Logger.getLogger(ConfirmController.class);

    @Autowired
    private GoodsService service;

    @Autowired
    private AddNewGoodsFormValidator validator;

    @Autowired
    private UserService userService;


    @RequestMapping(method = RequestMethod.GET)
    public String confirm(
            final Model model,
            final HttpSession session) {
        GoodsForm form = (GoodsForm) session.getAttribute("addNewGoods");
        session.removeAttribute("addNewGoods");
        model.addAttribute("isAuth",
                !SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser"));
        if (form == null) {
            return "redirect:/";
        } else {
            session.setAttribute("images", form.getImageUrl());
            model.addAttribute("goods", form);
        }
        return "home/confirm_announcement";
    }


    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute final GoodsForm form,
                         final Model model,
                         final HttpSession session) {
        long goodsId = 0;
        boolean isAuth = SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser";

        //form validation
        final Map<String, String> errors = validator.validate(form);
        if (errors.size() != 0) {
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth", isAuth);
            return "home/confirm_announcement";
        }
        try {
            //adding announcement
            User user = null;
            try {
                user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
            } catch (ServiceException e) {
                logger.error("An error appeared on getting user from repository ", e);
            }
            Goods goods = form.toGoods(user);
            goods.setImageUrl((List<String>) session.getAttribute("images"));
            goodsId = service.submitGoods(goods, new LinkedList<MultipartFile>());
        } catch (GoodsException e) {
            logger.error("An error appeared on submitting goods " + e.getMessage());
            return "home/error";
        }

        return "redirect:/see_announcement?announcement_id=" + goodsId;
    }
}
