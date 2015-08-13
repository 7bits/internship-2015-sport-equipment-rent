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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by awemath on 7/23/15.
 */
@Controller
public class AddAnnouncementController {
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
        model.addAttribute("isAuth", SecurityContextHolder.getContext().getAuthentication().getName()!="anonymousUser");
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
        final Map<String, String> errors = validator.validate(form);
        boolean isAuth = SecurityContextHolder.getContext().getAuthentication().getName() != "anonymousUser";
        if (errors.size() != 0) {
            // Если есть ошибки в форме, то снова рендерим главную страницу
            model.addAttribute("goods", form);
            model.addAttribute("errors", errors);
            model.addAttribute("isAuth", isAuth);
            LOG.info("Adding form contains errors.");
            return "home/add_announcement";
        }
        if(isAuth){
            try {
                service.save(form);
            } catch (GoodsException e) {
                LOG.info(e.getMessage());
            }
        }else{
            session.setAttribute("addNewGoods", form);
            session.setAttribute("firstImage", firstImage);
            session.setAttribute("secondImage", secondImage);
            session.setAttribute("thirdImage", thirdImage);
            return "redirect:/login";
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
        if(firstImage!=null)
            try {
                byte[] bytes = firstImage.getBytes();
                String fileName= "src/main/resources/public/img/upload/img/" + goods.get(0).getId()+"_1"+firstImage.getOriginalFilename();
                String nameForBase = "img/upload/img/" + goods.get(0).getId()+"_1"+firstImage.getOriginalFilename();

                File file = new File(fileName);

                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(file));
                stream.write(bytes);
                stream.close();
                service.addImage(goods.get(0).getId(), nameForBase);
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        if(secondImage!=null)
            try {
                byte[] bytes = secondImage.getBytes();
                String fileName= "src/main/resources/public/img/upload/img/" + goods.get(0).getId()+"_2"+secondImage.getOriginalFilename();
                String nameForBase = "img/upload/img/" + goods.get(0).getId()+"_2"+secondImage.getOriginalFilename();

                File file = new File(fileName);

                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(file));
                stream.write(bytes);
                stream.close();
                service.addImage(goods.get(0).getId(), nameForBase);
            } catch (Exception e) {
                LOG.error(e.getMessage());
            }
        if(thirdImage!=null)
        try {
            byte[] bytes = thirdImage.getBytes();
            String fileName= "src/main/resources/public/img/upload/img/" + goods.get(0).getId()+"_3"+thirdImage.getOriginalFilename();
            String nameForBase = "img/upload/img/" + goods.get(0).getId()+"_3"+thirdImage.getOriginalFilename();

            File file = new File(fileName);

            BufferedOutputStream stream =
                    new BufferedOutputStream(new FileOutputStream(file));
            stream.write(bytes);
            stream.close();
            service.addImage(goods.get(0).getId(), nameForBase);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
        return "redirect:/see_announcement?announcement_id="+goods.get(0).getId();
    }


}
