package it.sevenbits.service;

import it.sevenbits.core.repository.DealRepository;
import it.sevenbits.domain.Deal;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
import it.sevenbits.service.exceptions.DealServiceException;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.web.controllers.MailSubmissionController;
import org.joda.time.DateTime;
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

    @Autowired
    MailSubmissionController mail;

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

    public void handed(long dealId, boolean isHanded) throws DealServiceException {
        Deal deal = getDeal(dealId);
        User landlord = null;
        try {
            landlord = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (UserServiceException e) {
            throw new DealServiceException("An error appeared on getting user from repo", e);
        }
        if (deal.getLandlordId() != landlord.getId()) {
            throw new DealServiceException("");
        }
        if (deal.isAnswered()) {
            throw new DealServiceException("This announcement is allready answered");
        } else {
            deal.setIsHanded(isHanded);
            deal.setIsAnswered(true);
            update(deal);
            if (isHanded) {
                mail.sendConfirmationMail(deal);
            } else {
                mail.sendDeny(deal);
            }
        }
    }

    public void accept(long dealId, boolean isGet) throws DealServiceException {
        Deal deal = getDeal(dealId);
        User renting = null;
        try {
            renting = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (UserServiceException e) {
            throw new DealServiceException("An error appeared on getting user from repo", e);
        }
        if (renting.getId() != deal.getRentingId()) {
            throw new DealServiceException("");
        }
        if (isGet) {
            try {
                mail.sendClose(deal);
                updateRealStartDate(dealId);
            } catch (Exception e) {
                throw new DealServiceException("An error appeared on accepting deal: ", e);
            }
        }
    }

    public void close(long dealId, Deal deal) throws DealServiceException {
        deal = getDeal(dealId);
        User user = null;
        try {
            user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (UserServiceException e) {
            throw new DealServiceException("An error appeared on getting user from repo", e);
        }
        if (user.getId() != deal.getLandlordId()) {
            throw new DealServiceException("");
        }
        DateTime estimateStart = DateTime.parse(deal.getEstimateStartDate());
        DateTime estimateEnd = DateTime.parse(deal.getEstimateEndDate());
    }
}
