package it.sevenbits.web.controllers.user;

import it.sevenbits.web.domain.UpdateUserForm;
import it.sevenbits.web.domain.User;
import it.sevenbits.web.service.goods.GoodsException;
import it.sevenbits.web.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by awemath on 8/7/15.
 */
@Controller
@RequestMapping(value = "/update_profile")
public class UpdateProfileController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String update(final Model model){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = null;
        try {
            user = userService.getUser(name);
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        UpdateUserForm updateUserForm = UpdateUserForm.valueOf(user);
        model.addAttribute("user", updateUserForm);
        return "home/update_profile";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute UpdateUserForm form, final Model model){
        User user = User.valueOf(form);
        userService.update(user);
        return "";
    }

}
