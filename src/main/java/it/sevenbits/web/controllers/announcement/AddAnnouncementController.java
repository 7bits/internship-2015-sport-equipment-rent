package it.sevenbits.web.controllers.announcement;

import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.web.forms.GoodsForm;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.UserService;
import it.sevenbits.web.validators.AddNewGoodsFormValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by awemath on 7/23/15.
 */
@Controller
public class AddAnnouncementController {

    @Value("${resources.path}")
    private String resourcesPath;
    @Value("${resources.images}")
    private String imagesPath;
    @Value("${resources.default-announcement-image}")
    private String defaultImage;

    @Autowired
    AddNewGoodsFormValidator validator;

    @Autowired
    GoodsService service;

    @Autowired
    UserService userService;

    Logger LOG=Logger.getLogger(AddAnnouncementController.class);


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String index(final Model model) {
        // В модель добавим новый объект формы подписки
        model.addAttribute("goods", new GoodsForm());
        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser");
        // Так как нет аннотации @ResponseBody, то spring будет искать шаблон по адресу home/index
        // Если шаблона не будет найдено, то вернется 404 ошибка
        return "home/add_announcement";
    }


    @RequestMapping(value= "/add", method = RequestMethod.POST)
    public String submit(@ModelAttribute GoodsForm form, final Model model,
                         @RequestParam("firstImage") MultipartFile firstImage,
                         @RequestParam("secondImage") MultipartFile secondImage,
                         @RequestParam("thirdImage") MultipartFile thirdImage, HttpSession session) {
        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser");
        Map<String, String> errors = new HashMap<>();

        boolean isAuth = SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser";

        List<MultipartFile> images = new LinkedList<MultipartFile>();
        images.add(firstImage);
        images.add(secondImage);
        images.add(thirdImage);
        errors = validator.validate(form);

        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth", isAuth);
            LOG.info("Adding form contains errors.");
            return "home/add_announcement";
        }

        long goodsId = 0;
        if(!isAuth) {
            session.setAttribute("addNewGoods", form);
        }
        try {
            goodsId = service.submitGoods(form, images);
        } catch (GoodsException e) {
            LOG.error(e.getMessage());
            //exception
        } catch (UserServiceException e) {
            e.printStackTrace();
        }
        if(!isAuth) {
            return "redirect:/login";
        }

        return "redirect:/see_announcement?announcement_id="+goodsId;
    }






}
