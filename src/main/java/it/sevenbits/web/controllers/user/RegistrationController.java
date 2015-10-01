package it.sevenbits.web.controllers.user;

import it.sevenbits.domain.User;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.UserService;
import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.web.forms.RegistrationForm;
import it.sevenbits.web.validators.AddNewRegistrationFormValidator;
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
 * Created by awemath on 7/14/15.
 */

@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AddNewRegistrationFormValidator validator;
    private Logger logger = Logger.getLogger(RegistrationController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String login(final Model model) {
        model.addAttribute("registration", new RegistrationForm());
        return "home/registration";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute final RegistrationForm form,
                         final Model model) {
        final Map<String, String> errors = validator.validate(form);
        if (errors.size() != 0) {
            model.addAttribute("user", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth",
                    SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser");
            logger.info("Registration form contains errors.");
            return "home/registration";
        }
        //create model
        final User user = new User();
        user.setEmail(form.geteMail());
        user.setFirstName(form.getFirstName());
        user.setPass(BCrypt.hashpw(form.getPassword(), BCrypt.gensalt()));
        //save model
        try {
            userService.save(user);
        } catch (UserServiceException e) {
            logger.error("An error appeared on saving user: " + e.getMessage());
            return "home/error";
        }
        return "redirect:/confirm";
    }
}
