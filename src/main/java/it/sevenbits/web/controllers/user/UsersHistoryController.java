package it.sevenbits.web.controllers.user;

import it.sevenbits.web.domain.Deal;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.views.HistoryRowView;
import it.sevenbits.web.domain.User;
import it.sevenbits.service.DealService;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.GoodsService;
import it.sevenbits.service.UserService;
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
@RequestMapping(value="/history")
public class UsersHistoryController {

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    DealService dealService;


    @RequestMapping(method = RequestMethod.GET)
    public String history(final Model model){
        User user = null;
        try {
            user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (GoodsException e) {
            e.printStackTrace();
        }
        List<Deal> deals= dealService.getDealsOfUser(user.getId());
        List<HistoryRowView> table = new LinkedList<HistoryRowView>();
        for(int i=0;i<deals.size();i++){
            HistoryRowView row = new HistoryRowView();
            row.setStartDate(deals.get(i).getRealStartDate().substring(0, deals.get(i).getRealStartDate().indexOf('.')));
            row.setEndDate(deals.get(i).getRealEndDate().substring(0, deals.get(i).getRealEndDate().indexOf('.')));
            User renting = null;
            try {
                renting = userService.getUser(deals.get(i).getRentingId());
            } catch (GoodsException e) {
                e.printStackTrace();
            }
            row.setRenting(renting.getFirstName());
            Goods goods = null;
            try {
                goods = goodsService.getGoods(deals.get(i).getGoodsId());
            } catch (GoodsException e) {
                e.printStackTrace();
            }
            row.setTitle(goods.getTitle());
            table.add(row);
        }
        model.addAttribute("isAuth", true);
        model.addAttribute("userHistory", table);
        return "home/history";
    }
}
