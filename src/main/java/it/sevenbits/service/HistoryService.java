package it.sevenbits.service;

import it.sevenbits.domain.Deal;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.HistoryRow;
import it.sevenbits.domain.User;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.web.views.HistoryRowView;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<HistoryRow> getUsersHistory(User user) throws UserServiceException {
        List<Deal> deals = dealService.getDealsOfUser(user.getId());
        List<HistoryRow> table = new LinkedList<HistoryRow>();
        for (int i = 0; i < deals.size(); i++) {
            HistoryRow row = new HistoryRow();
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
            row.setRentingId(String.valueOf(renting.getId()));
            row.setRentingImage(renting.getImageUrl());
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
