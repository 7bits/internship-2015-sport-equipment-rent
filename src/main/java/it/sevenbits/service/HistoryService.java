package it.sevenbits.service;

import it.sevenbits.domain.Deal;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.HistoryRow;
import it.sevenbits.domain.User;
import it.sevenbits.service.exceptions.DealServiceException;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.exceptions.ServiceException;
import it.sevenbits.service.exceptions.UserServiceException;
import org.apache.log4j.Logger;
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

    Logger logger = Logger.getLogger(HistoryService.class);

    public List<HistoryRow> getUsersHistory(final User user) throws ServiceException {
        List<Deal> deals = null;
        try {
            deals = dealService.getDealsOfUser(user.getId());
        } catch (ServiceException e) {
            logger.error("An error appeared on getting users history", e);
            throw new ServiceException("An error appeared on getting users history", e);
        }
        List<HistoryRow> table = new LinkedList<HistoryRow>();
        for (int i = 0; i < deals.size(); i++) {
            HistoryRow row = new HistoryRow();
            row.setStartDate(deals.get(i).getRealStartDate()
                    .substring(0, deals.get(i).getRealStartDate().indexOf('.')));
            row.setEndDate(deals.get(i).getRealEndDate().substring(0, deals.get(i).getRealEndDate().indexOf('.')));
            User renting = null;
            try {
                renting = userService.getUser(deals.get(i).getRentingId());
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            row.setRenting(renting.getFirstName());
            row.setRentingId(String.valueOf(renting.getId()));
            row.setRentingImage(renting.getImageUrl());
            Goods goods = null;
            try {
                goods = goodsService.getGoods(deals.get(i).getGoodsId());
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            row.setTitle(goods.getTitle());
            table.add(row);
        }
        return table;

    }
}
