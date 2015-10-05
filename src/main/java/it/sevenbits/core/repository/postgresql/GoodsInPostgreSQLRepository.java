package it.sevenbits.core.repository.postgresql;

import it.sevenbits.core.mappers.GoodsMapper;
import it.sevenbits.core.repository.GoodsRepository;
import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.domain.Goods;
import it.sevenbits.domain.Image;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by awemath on 7/8/15.
 */
@Repository
@Qualifier(value = "goodsInPostgreSQLrepository")
public class GoodsInPostgreSQLRepository implements GoodsRepository {

    private static Logger logger = Logger.getLogger(GoodsInPostgreSQLRepository.class);

    @Autowired
    private GoodsMapper mapper;


    @Override
    public void save(final Goods goods) throws RepositoryException {
        if (goods == null) {
            logger.error("Goods is null");
        }
        try {
            mapper.save(goods);
            logger.info("New goods saved: " + goods.toString());
        } catch (Exception e) {
            logger.error("An error occurred while saving subscription: " + goods.toString() + e.getMessage());
            throw new RepositoryException("An error occurred while saving subscription: ", e);
        }
    }

    @Override
    public List<Goods> findAll() throws RepositoryException {
        try {
            return mapper.findAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error occurred while selected subscription: ", e);
        }
    }


    public Goods getGoods(final long id) throws RepositoryException {
        try {
            return mapper.getGoods(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error occurred while selected goods: ", e);
        }
    }

    @Override
    public List<Goods> getGoodsByAuthorId(final long id) {
        return mapper.getGoodsByAuthorId(id);
    }

    @Override
    public void update(final Goods form) {
        mapper.update(form);
    }

    @Override
    public void delete(final Long id) {
        mapper.delete(id);
    }

    @Override
    public List<Image> imageUrl(final long goodsId) {
        List<Image> images = mapper.getImages(goodsId);
        return images;
    }

    @Override
    public Image getImageForGoods(final long id) {
        return mapper.getImageForGoods(id);
    }

    @Override
    public void addImage(final long goodsId, final String url) {
        mapper.addImage(goodsId, url);
    }

    @Override
    public void updateImage(final String nameForBase, final Image image) {
        mapper.updateImage(nameForBase, image.getId());
    }

    @Override
    public boolean checkStatus(final Goods goods) {
        return mapper.dealsCount(goods) == 0;
    }
}
