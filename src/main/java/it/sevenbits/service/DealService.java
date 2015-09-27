package it.sevenbits.service;

import it.sevenbits.core.repository.DealRepository;
import it.sevenbits.domain.Deal;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
import it.sevenbits.service.exceptions.DealServiceException;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.web.controllers.MailSubmissionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by awemath on 7/21/15.
 */
@Service
public class DealService {
    @Autowired
    @Qualifier(value = "dealInPostgreSQLRepository")
    private DealRepository repository;

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private MailSubmissionController mailSubmissionController;

    @Autowired
    private UserService userService;

    public void save(final Deal deal) {
        repository.save(deal);
    }

    public Deal getDeal(final long dealId) {
        return repository.getDeal(dealId);
    }

    public long getId(final Deal deal) {
        return repository.getId(deal);
    }


    public void update(final Deal deal) {
        repository.update(deal);
    }


    public boolean isExist(final Deal deal) {
        return repository.isExist(deal);
    }

    public void deleteAllOnGoods(final long goodsId) {
        repository.deleteAllOnGoods(goodsId);
    }

    public void updateRealStartDate(final long dealId) {
        repository.updateRealStartDate(dealId);
    }

    public void updateRealEndDate(final long dealId) {
        repository.updateRealEndDate(dealId);
    }

    public List<Deal> getOpenWithId(final long goodsId) {
        return repository.getOpenWithId(goodsId);
    }

    public List<Deal> getDealsOfUser(final Long id) {
        return repository.getDealsOfUser(id);
    }


    public void submitDeal(final Deal deal,
                           final String announcementId)
            throws GoodsException, UserServiceException, DealServiceException {
        Goods goods = null;
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(name);
        goods = goodsService.getGoods(Long.valueOf(announcementId));
        User landlord = userService.getUser(goods.getAuthorId());
        if (user.getEmail().equals(landlord.getEmail())) {
            throw new DealServiceException("You cant pick your announcement");
        }
        if (!isExist(deal)) {
            save(deal);
            mailSubmissionController.sendHtmlEmail(deal);
        } else {
            throw new DealServiceException("You already tried to pick it up");
        }



    }
}
