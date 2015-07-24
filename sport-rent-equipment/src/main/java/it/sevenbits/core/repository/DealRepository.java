package it.sevenbits.core.repository;

import it.sevenbits.web.domain.Deal;

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
}
