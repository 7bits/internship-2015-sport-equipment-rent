package it.sevenbits.web.controllers.user;

import it.sevenbits.domain.HistoryRow;
import it.sevenbits.domain.User;
import it.sevenbits.service.HistoryService;
import it.sevenbits.service.UserService;
import it.sevenbits.service.exceptions.ServiceException;
import it.sevenbits.web.views.HistoryRowView;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by awemath on 8/19/15.
 */
@Controller
@RequestMapping(value = "/history")
public class UsersHistoryController {
    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(UsersHistoryController.class);
    @RequestMapping(method = RequestMethod.GET)
    public String history(final Model model) {
        model.addAttribute("isAuth", true);
        try {
            User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
            List<HistoryRow> historyTable = historyService.getUsersHistory(user);
            List<HistoryRowView> historyTableView = new LinkedList<HistoryRowView>();
            for (int i = 0; i < historyTable.size(); i++) {
                historyTableView.add(i, HistoryRowView.valueOf(historyTable.get(i)));
            }
            model.addAttribute("userHistory", historyTableView);
        } catch (ServiceException e) {
            logger.error("Cant show history of user " + e.getMessage());
            return "home/error";
        }
        return "home/history";
    }
}
