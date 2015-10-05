package it.sevenbits.core.repository;

import it.sevenbits.domain.Deal;

import java.util.List;

/**
 * Created by awemath on 7/21/15.
 */
public interface DealRepository {
    void save(Deal deal) throws RepositoryException;
    Deal getDeal(long dealId) throws RepositoryException;
    void update(Deal deal) throws RepositoryException;

    long getId(Deal deal) throws RepositoryException;

    boolean isExist(Deal deal) throws RepositoryException;

    void deleteAllOnGoods(long goodsId) throws RepositoryException;

    void updateRealStartDate(long dealId) throws RepositoryException;

    void updateRealEndDate(long dealId) throws RepositoryException;

    List<Deal> getOpenWithId(long goodsId) throws RepositoryException;

    List<Deal> getDealsOfUser(Long id) throws RepositoryException;
}
