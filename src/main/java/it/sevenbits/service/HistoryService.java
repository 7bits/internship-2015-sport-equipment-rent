package it.sevenbits.service;

import it.sevenbits.domain.Deal;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.web.views.HistoryRowView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by awemath on 9/27/15.
 */
@Service
public class HistoryService {
    @Autowired
    private UserService userService;

    @Autowired
    private DealService dealService;

    @Autowired
    private GoodsService goodsService;

    public List<HistoryRowView> getUsersHistory() throws UserServiceException {
        User user = null;

        user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());

        List<Deal> deals = dealService.getDealsOfUser(user.getId());
        List<HistoryRowView> table = new LinkedList<HistoryRowView>();
        for (int i = 0; i < deals.size(); i++) {
            HistoryRowView row = new HistoryRowView();
            row.setStartDate(deals.get(i).getRealStartDate()
                    .substring(0, deals.get(i).getRealStartDate().indexOf('.')));
            row.setEndDate(deals.get(i).getRealEndDate().substring(0, deals.get(i).getRealEndDate().indexOf('.')));
            User renting = null;
            try {
                renting = userService.getUser(deals.get(i).getRentingId());
            } catch (UserServiceException e) {
                e.printStackTrace();
            }
            row.setRenting(renting.getFirstName());
            Goods goods = null;
            try {
                goods = goodsService.getGoods(deals.get(i).getGoodsId());
            } catch (UserServiceException e) {
                e.printStackTrace();
            } catch (GoodsException e) {
                e.printStackTrace();
            }
            row.setTitle(goods.getTitle());
            table.add(row);
        }
        return table;

    }
}
