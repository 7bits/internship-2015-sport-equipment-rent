package it.sevenbits.web.controllers.announcement;

import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.User;
import it.sevenbits.web.service.goods.DealService;
import it.sevenbits.web.service.goods.GoodsException;
import it.sevenbits.web.service.goods.GoodsService;
import it.sevenbits.web.service.users.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by awemath on 7/24/15.
 */
@Controller
@RequestMapping(value="/delete")
public class DeleteController {

    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    DealService dealService;
    Logger LOG = Logger.getLogger(DeleteController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String delete(@RequestParam(value="announcement_id", required = false) String announcementId){
        User user= null;
        try {
            user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (GoodsException e) {
            LOG.error("An error occured at delete on getting user: "+e.getMessage());
        }
        Goods goods = null;
        try {
            goods = goodsService.getGoods(Long.valueOf(announcementId));
        } catch (GoodsException e) {
            LOG.error("An error occured at delete on getting goods: " + e.getMessage());
        }
        if(goods.getAuthorId()!=user.getId()){
            return "home/error_message";
        }
        dealService.deleteAllOnGoods(goods.getId());
        goodsService.delete(goods.getId());

        return "redirect:/";
    }
}
