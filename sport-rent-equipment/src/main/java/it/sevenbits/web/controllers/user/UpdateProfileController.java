package it.sevenbits.web.controllers.user;

import it.sevenbits.web.domain.UpdateUserForm;
import it.sevenbits.web.domain.User;
import it.sevenbits.web.service.goods.GoodsException;
import it.sevenbits.web.service.users.UpdateFieldValidator;
import it.sevenbits.web.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by awemath on 8/7/15.
 */
@Controller
@RequestMapping(value = "/update_profile")
public class UpdateProfileController {
    @Autowired
    UserService userService;

    @Autowired
    UpdateFieldValidator validator;

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
        return "home/update-profile";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute UpdateUserForm form, final Model model){
        final Map<String, String> errors =validator.validate(form);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (form.getPass() != "" && form.getPass()!=null) {
            User user = null;
            try {
                user = userService.getUser(name);
            } catch (GoodsException e) {
                e.printStackTrace();
            }
            if(user.getPass().equals(form.getPass())){
                if(form.getNewPass().length()>0){
                    user.setPass(form.getNewPass());
                    userService.updatePass(user);
                }else{
                    errors.put("", "Заполните новый пароль");
                }
            }else{
                errors.put("", "Неверно введен текущий пароль");
            }
        }
        if(!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("user", form);
            return "home/update-profile";

        }
        User user = User.valueOf(form);
        try {
            user.setId(userService.getUser(name).getId());
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        userService.update(user);
        return "redirect:/personal_area";
    }

}
