package it.sevenbits.core.repository;

import it.sevenbits.web.domain.Goods;

import java.util.List;

/**
 * Created by awemath on 7/8/15.
 */
public interface GoodsRepository {
        void save(final Goods goods) throws RepositoryException;
        List<Goods> findAll() throws RepositoryException;
        Goods getGoods(long id) throws RepositoryException;
}
