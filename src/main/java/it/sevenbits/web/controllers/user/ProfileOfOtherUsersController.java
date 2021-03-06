package it.sevenbits.web.controllers.user;

import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.UserService;
import it.sevenbits.service.exceptions.ServiceException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * Created by awemath on 8/4/15.
 */
@Controller
@RequestMapping(value = "/profile")
public class ProfileOfOtherUsersController {
    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    private Logger logger = Logger.getLogger(ProfileOfOtherUsersController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String login(@RequestParam(value = "user_id", required = false) final String userId, final Model model) {
        User user = null;
        try {
            user = userService.getUser(Long.valueOf(userId));
        } catch (ServiceException e) {
            return "home/error";
        } catch (NumberFormatException e) {
            return "home/error";
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        if (name.equals(user.getEmail())) {
            return "redirect:/personal_area";
        }
        model.addAttribute("isAuth", !name.equals("anonymousUser"));
        model.addAttribute("user", user);
        List<Goods> goods = goodsService.getGoodsByAuthorId(user.getId());
        model.addAttribute("goods", goods);
        return "home/other_users_profile";
    }
}