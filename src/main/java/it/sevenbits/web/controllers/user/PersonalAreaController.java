package it.sevenbits.web.controllers.user;

import it.sevenbits.domain.User;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.UserService;
import it.sevenbits.service.exceptions.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by awemath on 7/17/15.
 */
@Controller
@RequestMapping(value = "/personal_area")
public class PersonalAreaController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserService userService;
    @RequestMapping(method = RequestMethod.GET)
    public String seePersonalArea(final Model model) {
        User user = null;
        try {
            user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
        model.addAttribute("goods", goodsService.getGoodsByAuthorId(user.getId()));
        model.addAttribute("user", user);
        return "home/personal_area";
    }
}
