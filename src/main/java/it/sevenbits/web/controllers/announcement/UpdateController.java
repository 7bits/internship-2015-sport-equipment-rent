package it.sevenbits.web.controllers.announcement;

import it.sevenbits.domain.Goods;
import it.sevenbits.web.forms.GoodsForm;
import it.sevenbits.domain.User;
import it.sevenbits.web.validators.AddNewGoodsFormValidator;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.UserService;
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
    GoodsService goodsService;
    @Autowired
    UserService userService;

    Logger LOG = Logger.getLogger(UpdateController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String update(@RequestParam(value="announcement_id", required = false) String announcementId, final Model model){
        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        String name =  SecurityContextHolder.getContext().getAuthentication().getName();
        User user = null;
        try {
            user = userService.getUser(name);
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        Goods goods = null;
        try {
            goods =  goodsService.getGoods(Long.valueOf(announcementId));
        } catch (GoodsException e) {
            LOG.error("An error occured while picking goods from database at UpdateController class: "+e.getMessage());
            return "home/error";
        }
        if(user.getId()!= goods.getAuthorId()){
            return "redirect:/see_announcement?announcement_id="+announcementId;
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
    public String submit(@RequestParam(value="announcement_id", required = false) long announcementId,
                         @RequestParam("firstImage") MultipartFile firstImage,
                         @RequestParam("secondImage") MultipartFile secondImage,
                         @RequestParam("thirdImage") MultipartFile thirdImage,
                         @RequestParam(value = "firstImageDelete", required = false) boolean firstImageDelete,
                         @RequestParam(value = "secondImageDelete", required = false) boolean secondImageDelete,
                         @RequestParam(value = "thirdImageDelete", required = false) boolean thirdImageDelete,
                         final Model model, @ModelAttribute GoodsForm form){
        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
        final Map<String, String> errors = validator.validate(form);
        List<MultipartFile> images = new LinkedList<MultipartFile>();
        images.add(firstImage);
        images.add(secondImage);
        images.add(thirdImage);
        boolean deleted[] = new boolean [3];
        deleted[0] = firstImageDelete;
        deleted[1] = secondImageDelete;
        deleted[2] = thirdImageDelete;
        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
            LOG.info("Update form contains errors.");
            return "home/update_announcement";
        }
        goodsService.updateAnnouncement(images, deleted, form, announcementId, errors);
        return "redirect:/see_announcement?announcement_id="+announcementId;
    }

}
