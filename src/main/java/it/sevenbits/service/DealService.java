package it.sevenbits.service;

import it.sevenbits.core.repository.DealRepository;
import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.core.repository.postgresql.DealInPostgreSQLRepository;
import it.sevenbits.domain.Deal;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
import it.sevenbits.service.exceptions.DealServiceException;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.web.controllers.MailSubmissionController;
import org.apache.log4j.Logger;
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
    private MailSubmissionController mail;

    private Logger logger = Logger.getLogger(DealService.class);

    public void save(final Deal deal) throws DealServiceException {
        try {
            repository.save(deal);
        } catch (RepositoryException e) {
            logger.error("An error appeared on saving deal", e);
            throw new DealServiceException("An error appeared on saving deal", e);
        }
    }

    public Deal getDeal(final long dealId) throws DealServiceException {
        try {
            return repository.getDeal(dealId);
        } catch (RepositoryException e) {
            logger.error("An error appeared on getting deal", e);
            throw new DealServiceException("An error appeared on getting deal", e);
        }
    }

    public long getId(final Deal deal) throws DealServiceException {
        try {
            return repository.getId(deal);
        } catch (RepositoryException e) {
            logger.error("An error appeared on getting deals id", e);
            throw new DealServiceException("An error appeared on getting deals id", e);
        }
    }


    public void update(final Deal deal) throws DealServiceException {
        try {
            repository.update(deal);
        } catch (RepositoryException e) {
            logger.error("An error appeared on updating deal", e);
            throw new DealServiceException("An error appeared on updating deal", e);
        }
    }


    public boolean isExist(final Deal deal) throws DealServiceException {
        try {
            return repository.isExist(deal);
        } catch (RepositoryException e) {
            logger.error("An error appeared on checking exist deal", e);
            throw new DealServiceException("An error appeared on checking exist deal", e);
        }
    }

    public void deleteAllOnGoods(final long goodsId) throws DealServiceException {
        try {
            repository.deleteAllOnGoods(goodsId);
        } catch (RepositoryException e) {
            logger.error("An error appeared on deleting deals of the goods", e);
            throw new DealServiceException("An error appeared on deleting deals of the goods", e);
        }
    }

    public void updateRealStartDate(final long dealId) throws DealServiceException {
        try {
            repository.updateRealStartDate(dealId);
        } catch (RepositoryException e) {
            logger.error("An error appeared on updating real start date of the deals", e);
            throw new DealServiceException("An error appeared on updating real start date of the deals", e);
        }
    }

    public void updateRealEndDate(final long dealId) throws DealServiceException {
        try {
            repository.updateRealEndDate(dealId);
        } catch (RepositoryException e) {
            logger.error("An error appeared on updating real end date of the deals", e);
            throw new DealServiceException("An error appeared on updating real end date of the deals", e);
        }
    }

    public List<Deal> getOpenWithId(final long goodsId) throws DealServiceException {
        try {
            return repository.getOpenWithId(goodsId);
        } catch (RepositoryException e) {
            logger.error("An error appeared on getting open deals of the goods", e);
            throw new DealServiceException("An error appeared on getting open deals of the goods", e);
        }
    }

    public List<Deal> getDealsOfUser(final Long id) throws DealServiceException {
        try {
            return repository.getDealsOfUser(id);
        } catch (RepositoryException e) {
            logger.error("An error appeared on getting deals of the user", e);
            throw new DealServiceException("An error appeared on getting deals of the user", e);
        }
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

    public void handed(
            final long dealId,
            final boolean isHanded) throws DealServiceException {
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

    public void accept(
            final long dealId,
            final boolean isGet) throws DealServiceException {
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

    public void close(
            final long dealId) throws DealServiceException {
        Deal deal = null;
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
