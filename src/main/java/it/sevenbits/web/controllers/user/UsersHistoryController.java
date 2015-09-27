package it.sevenbits.web.controllers.user;

import it.sevenbits.service.HistoryService;
import it.sevenbits.service.exceptions.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by awemath on 8/19/15.
 */
@Controller
@RequestMapping(value = "/history")
public class UsersHistoryController {
    @Autowired
    private HistoryService historyService;

    @RequestMapping(method = RequestMethod.GET)
    public String history(final Model model) {
        model.addAttribute("isAuth", true);
        try {
            model.addAttribute("userHistory", historyService.getUsersHistory());
        } catch (UserServiceException e) {
            //exception
        }
        return "home/history";
    }
}
