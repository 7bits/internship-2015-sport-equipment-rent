package it.sevenbits.core.repository.dealrepository;

import it.sevenbits.web.domain.Deal;

import java.util.List;

/**
 * Created by awemath on 7/21/15.
 */
public interface DealRepository {
    void save(Deal deal);
    Deal getDeal(long dealId);
    void update(Deal deal);

    long getId(Deal deal);

    boolean isExist(Deal deal);

    void deleteAllOnGoods(long goodsId);

    void updateRealStartDate(long dealId);

    void updateRealEndDate(long dealId);

    List<Deal> getOpenWithId(long goodsId);

    List<Deal> getDealsOfUser(Long id);
}
