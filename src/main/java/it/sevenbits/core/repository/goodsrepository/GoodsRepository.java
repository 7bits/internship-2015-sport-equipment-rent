package it.sevenbits.core.repository.goodsrepository;

import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.Image;

import java.util.List;

/**
 * Created by awemath on 7/8/15.
 */
public interface GoodsRepository {
        void save(final Goods goods) throws RepositoryException;
        List<Goods> findAll() throws RepositoryException;
        Goods getGoods(long id) throws RepositoryException;
        List <Goods> getGoodsByAuthorId(long id);
        void update(Goods form);

        void delete(Long id);
        List<Image> imageUrl(long goodsId);

        Image getImageForGoods(long id);

        void addImage(long goodsId, String url);

        void updateImage(String nameForBase, Image image);

        boolean checkStatus(Goods goods);
}
