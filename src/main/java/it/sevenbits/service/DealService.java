package it.sevenbits.service;

import it.sevenbits.core.repository.DealRepository;
import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.domain.Deal;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;
import it.sevenbits.service.exceptions.*;
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

    private void save(final Deal deal) throws ServiceException {
        try {
            repository.save(deal);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public Deal getDeal(final long dealId) throws ServiceException {
        try {
            return repository.getDeal(dealId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public long getId(final Deal deal) throws ServiceException {
        try {
            return repository.getId(deal);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }


    public void update(final Deal deal) throws ServiceException {
        try {
            repository.update(deal);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }


    public boolean isExist(final Deal deal) throws ServiceException {
        try {
            return repository.isExist(deal);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteAllOnGoods(final long goodsId) throws ServiceException {
        try {
            repository.deleteAllOnGoods(goodsId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void updateRealStartDate(final long dealId) throws ServiceException {
        try {
            repository.updateRealStartDate(dealId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public void updateRealEndDate(final long dealId) throws ServiceException {
        try {
            repository.updateRealEndDate(dealId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public List<Deal> getOpenWithId(final long goodsId) throws ServiceException {
        try {
            return repository.getOpenWithId(goodsId);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }

    public List<Deal> getDealsOfUser(final Long id) throws ServiceException {
        try {
            return repository.getDealsOfUser(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
    }


    public void submitDeal(final Deal deal,
                           final String announcementId,
                           final User user)
            throws ServiceException, YourAnnouncementException, RepeatedDealException {
        Goods goods = null;
        try {

            goods = goodsService.getGoods(Long.valueOf(announcementId));
            User landlord = userService.getUser(goods.getAuthorId());
            deal.setLandlordId(landlord.getId());
            deal.setRentingId(user.getId());
            deal.setGoodsId(goods.getId());
            if (user.getEmail().equals(landlord.getEmail())) {
                throw new YourAnnouncementException();
            }
            if (!isExist(deal)) {
                save(deal);
                mailSubmissionController.sendHtmlEmail(deal);
            } else {
                throw new RepeatedDealException();
            }
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }
    }

    public void handed(
            final long dealId,
            final boolean isHanded,
            final User landlord) throws AccessDeniedException, AlreadyAnsweredException, ServiceException {
        try {
            Deal deal = getDeal(dealId);

            if (landlord != null && deal.getLandlordId() != landlord.getId()) {
                throw new AccessDeniedException();
            }
            if (deal.isAnswered()) {
                throw new AlreadyAnsweredException();
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
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }
    }

    public void accept(
            final long dealId,
            final boolean isGet) throws ServiceException, AccessDeniedException {
        Deal deal = getDeal(dealId);
        User renting = null;
        try {
            renting = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }
        if (renting.getId() != deal.getRentingId()) {
            throw new AccessDeniedException();
        }
        if (isGet) {
            try {
                mail.sendClose(deal);
                updateRealStartDate(dealId);
            } catch (Exception e) {
                throw new ServiceException(e);
            }
        }
    }

    public void close(
            final long dealId) throws ServiceException, AccessDeniedException {
        Deal deal = null;
        deal = getDeal(dealId);
        User user = null;
        user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        if (user.getId() != deal.getLandlordId()) {
            throw new AccessDeniedException();
        }
        DateTime estimateStart = DateTime.parse(deal.getEstimateStartDate());
        DateTime estimateEnd = DateTime.parse(deal.getEstimateEndDate());
    }
}
