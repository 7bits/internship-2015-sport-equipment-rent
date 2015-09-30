package it.sevenbits.web.controllers.user;

import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.web.forms.UpdateUserForm;
import it.sevenbits.domain.User;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.validators.UpdateFieldValidator;
import it.sevenbits.service.UserService;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
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
    private UserService userService;

    @Autowired
    private UpdateFieldValidator validator;

    private Logger LOG = Logger.getLogger(UpdateProfileController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String update(final Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = null;
        try {
            user = userService.getUser(name);
        } catch (UserServiceException e) {
            LOG.error("An error appeared on getting user: " + e.getMessage());
            return "home/error";
        }
        UpdateUserForm updateUserForm = UpdateUserForm.valueOf(user);
        model.addAttribute("user", updateUserForm);
        return "home/update-profile";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute UpdateUserForm form, final Model model) {
        final Map<String, String> errors = validator.validate(form);
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (form.getPass() != "" && form.getPass() != null) {
            User user = null;
            try {
                user = userService.getUser(name);
            } catch (UserServiceException e) {
                LOG.error("An error appeared on getting user: " + e.getMessage());
            }
            if (BCrypt.checkpw(form.getPass(), user.getPass())) {
                if (form.getNewPass().length() > 0) {
                    user.setPass(form.getNewPass());
                    userService.updatePass(user);
                } else {
                    errors.put("", "Заполните новый пароль");
                }
            } else {
                errors.put("", "Неверно введен текущий пароль");
            }
        }
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("user", form);
            return "home/update-profile";

        }
        User user = User.valueOf(form);
        try {
            user.setId(userService.getUser(name).getId());
        } catch (UserServiceException e) {
            LOG.error("An error appeared on getting user: " + e.getMessage());
        }
        userService.update(user);
        return "redirect:/personal_area";
    }

}
