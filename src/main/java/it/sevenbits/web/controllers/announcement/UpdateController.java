package it.sevenbits.web.controllers.announcement;

import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.UserService;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.exceptions.ServiceException;
import it.sevenbits.web.forms.GoodsForm;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by awemath on 7/22/15.
 */
@Controller
@RequestMapping(value = "/update")
public class UpdateController {
    @Value("${resources.path}")
    private String resourcesPath;
    @Value("${resources.images}")
    private String imagesPath;
    @Value("${resources.default-announcement-image}")
    private String defaultImage;

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(UpdateController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String update(@RequestParam(value = "announcement_id", required = false) final String announcementId,
                         final Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("isAuth",
                !name.equals("anonymousUser"));
        User user = null;
        try {
            user = userService.getUser(name);
        } catch (ServiceException e) {
            logger.error("An error appeared on getting user from repository: " + e.getMessage());
            return "home/error";
        }
        Goods goods = null;
        try {
            goods = goodsService.getGoods(Long.valueOf(announcementId));
        } catch (ServiceException e) {
            logger.error("An error appeared while picking goods from repository: " + e.getMessage());
            return "home/error";
        }
        if (user.getId() != goods.getAuthorId()) {
            return "redirect:/see_announcement?announcement_id=" + announcementId;
        }
        GoodsForm form = GoodsForm.valueOf(goods);
        form.setId(Long.valueOf(announcementId));
        form.addImageUrl(goods.getImageUrl().get(0));
        form.addImageUrl(goods.getImageUrl().get(1));
        form.addImageUrl(goods.getImageUrl().get(2));

        model.addAttribute("goods", form);
        return "home/update_announcement";
    }


    @Autowired
    private AddNewGoodsFormValidator validator;

    @RequestMapping(method = RequestMethod.POST)
    public String submit(@RequestParam(value = "announcement_id", required = false) final String announcementId,
                         @RequestParam("firstImage") final MultipartFile firstImage,
                         @RequestParam("secondImage") final MultipartFile secondImage,
                         @RequestParam("thirdImage") final MultipartFile thirdImage,
                         @RequestParam(value = "firstImageDelete", required = false) final boolean firstImageDelete,
                         @RequestParam(value = "secondImageDelete", required = false) final boolean secondImageDelete,
                         @RequestParam(value = "thirdImageDelete", required = false) final boolean thirdImageDelete,
                         final Model model,
                         @ModelAttribute final GoodsForm form) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("isAuth",
                 !name.equals("anonymousUser"));
        final Map<String, String> errors = validator.validate(form);
        List<MultipartFile> images = new LinkedList<MultipartFile>();
        images.add(firstImage);
        images.add(secondImage);
        images.add(thirdImage);
        boolean[] deleted = new boolean[3];
        deleted[0] = firstImageDelete;
        deleted[1] = secondImageDelete;
        deleted[2] = thirdImageDelete;

        if (errors.size() != 0) {
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            logger.info("Update form contains errors.");
            return "home/update_announcement";
        }
        User user = null;
        try {
            user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (ServiceException e) {
            logger.error("An error appeared while getting user from repository: " + e.getMessage());
            return "home/error";
        }
        Goods goods = form.toGoods(user);
        try {
            goodsService.updateAnnouncement(images, deleted, goods, Long.valueOf(announcementId));
        } catch (GoodsException e) {
            logger.error(e.getMessage());
            return "home/error";
        }
        return "redirect:/see_announcement?announcement_id=" + announcementId;

    }


}
