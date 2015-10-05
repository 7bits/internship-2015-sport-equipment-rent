package it.sevenbits.web.controllers.announcement;

import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
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
    private AddNewGoodsFormValidator validator;

    @Autowired
    private GoodsService service;

    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(AddAnnouncementController.class);


    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String index(final Model model) {
        model.addAttribute("goods", new GoodsForm());
        model.addAttribute("isAuth",
                SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser");
        return "home/add_announcement";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String submit(@ModelAttribute final GoodsForm form,
                         final Model model,
                         @RequestParam("firstImage") final MultipartFile firstImage,
                         @RequestParam("secondImage") final MultipartFile secondImage,
                         @RequestParam("thirdImage") final MultipartFile thirdImage,
                         final HttpSession session) {

        model.addAttribute("isAuth",
                SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser");
        Map<String, String> errors;

        boolean isAuth = SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser";
        //create list of images
        List<MultipartFile> images = new LinkedList<MultipartFile>();
        images.add(firstImage);
        images.add(secondImage);
        images.add(thirdImage);


        //form validation
        errors = validator.validate(form);
        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth", isAuth);
            return "home/add_announcement";
        }

        //adding announcement
        long goodsId = 0;
        User user = null;
        try {
            user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (UserServiceException e) {
            logger.error("An error appeared on getting user from repository" + e.getMessage());
            return "home/error";
        }
        Goods goods = null;
        if (isAuth) {
            goods = form.toGoods(user);
        } else {
            goods = form.toGoods();
        }
        try {
            goodsId = service.submitGoods(goods, images);
        } catch (GoodsException e) {
            logger.error("An error appeared on submting goods " + e.getMessage());
            return "home/error";
        }

        //web feature (submit announcement without auth)
        if (!isAuth) {

            goods.getImageUrl().forEach(form::addImageUrl);

            session.setAttribute("addNewGoods", form);
            return "redirect:/login";
        }

        return "redirect:/see_announcement?announcement_id=" + goodsId;
    }


}
