package it.sevenbits.web.controllers.announcement;

import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.GoodsForm;
import it.sevenbits.web.domain.User;
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
import java.util.Comparator;
import java.util.List;
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
        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        if(form==null) {
            return "redirect:/";
        }else{
            model.addAttribute("goods", form);
        }
        return "home/confirm_announcement";
    }
    @RequestMapping(method = RequestMethod.POST)
    public String submit(@ModelAttribute GoodsForm form, final Model model, HttpSession session) {
        final Map<String, String> errors = validator.validate(form);
        boolean isAuth = SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser";
        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth", isAuth);
            LOG.info("Adding form contains errors.");
            return "home/confirm_announcement";
        }

        try {
            service.save(form);
        } catch (GoodsException e) {
            LOG.info(e.getMessage());
        }

        try {
            model.addAttribute("goods", service.findAll());
        } catch (GoodsException e) {
            LOG.error("Error at the picking goods");
        }
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = null;
        try {
            user = userService.getUser(name);
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        List<Goods> goods = service.getGoodsByAuthorId(user.getId());
        goods.sort(new Comparator<Goods>() {
            @Override
            public int compare(Goods o1, Goods o2) {
                return o1.getId()<o2.getId()?1:-1;
            }
        });
        return "redirect:/see_announcement?announcement_id="+goods.get(0).getId();
    }
}
