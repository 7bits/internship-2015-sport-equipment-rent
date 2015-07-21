package it.sevenbits.web.controllers;

import it.sevenbits.web.domain.Deal;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.GoodsForm;
import it.sevenbits.web.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by awemath on 7/7/15.
 */
@Controller
public class HomeController {

    private static Logger LOG = Logger.getLogger(HomeController.class);
    @Autowired
    private GoodsService service;


    @Autowired
    private AddNewGoodsFormValidator validator;


    @RequestMapping(value = "/add_announcement", method = RequestMethod.GET)
    public String index(final Model model) {
        // В модель добавим новый объект формы подписки
        model.addAttribute("goods", new GoodsForm());
        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        // Так как нет аннотации @ResponseBody, то spring будет искать шаблон по адресу home/index
        // Если шаблона не будет найдено, то вернется 404 ошибка
        return "home/add_announcement";
    }


    @RequestMapping(value="/add_announcement", method = RequestMethod.POST)
    public String submit(@ModelAttribute GoodsForm form, final Model model){

        final Map<String, String> errors = validator.validate(form);
        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
            LOG.info("Subscription form contains errors.");
            return "home/add_announcement";
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
        return "home/index";
    }


    @RequestMapping(value="/", method = RequestMethod.GET)
    public String mainPage(final Model model){
        try {
            model.addAttribute("goods", service.findAll());
            model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        } catch (GoodsException e) {
            e.printStackTrace();
    }

        return "home/index";
    }




    @RequestMapping(value="/see_announcement", method = RequestMethod.GET)
    public String announcementPage(@RequestParam(value="announcement_id", required = false) String announcementId,  final Model model) {
        try {
            model.addAttribute("Goods", service.getGoods(Long.valueOf(announcementId)));
            model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        } catch (GoodsException e) {
            e.printStackTrace();
        }

        return "home/see_announcement";
    }

    @Autowired
    MailSubmissionController mailSubmissionController;

    @Autowired
    UserService userService;

    @Autowired
    DealService dealService;

    @RequestMapping(value="/getIt", method = RequestMethod.GET)
    public String getIt(@RequestParam(value="announcement_id", required = false) String announcementId, final Model model) {
        try {
            Goods goods = service.getGoods(Long.valueOf(announcementId));
            Deal deal = new Deal(goods.getAuthorId(), userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName()).getId(),
                    goods.getId());
            dealService.save(deal);
            mailSubmissionController.send(goods, deal);
        } catch (GoodsException e) {
            LOG.error("An error occured on the creating a deal: "+e.getMessage());
        }
        try {
            model.addAttribute("Goods", service.getGoods(Long.valueOf(announcementId)));
            model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser");
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        return "home/see_announcement";
    }


}
