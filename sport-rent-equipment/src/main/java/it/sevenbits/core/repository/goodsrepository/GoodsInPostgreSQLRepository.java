package it.sevenbits.core.repository.goodsrepository;

import it.sevenbits.core.mappers.GoodsMapper;
import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.ImageUrl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by awemath on 7/8/15.
 */
@Repository
@Qualifier(value="goodsInPostgreSQLrepository")
public class GoodsInPostgreSQLRepository implements GoodsRepository{

    private static Logger LOG= Logger.getLogger(GoodsInPostgreSQLRepository.class);

    @Autowired
    private GoodsMapper mapper;


    @Override
    public void save(Goods goods) throws RepositoryException {
        if(goods==null){
            LOG.error("Goods is null");
            throw new RepositoryException("goods is null");
        }
        try{
            mapper.save(goods);
            LOG.info("New goods saved: " + goods.toString());
        } catch (Exception e){
            LOG.error("An error occurred while saving subscription: " + e.getMessage());
            throw new RepositoryException("An error occurred while saving subscription: " + e.getMessage());
        }
    }

    @Override
    public List<Goods> findAll() throws RepositoryException {
        try{
            return mapper.findAll();
        } catch(Exception e){
            LOG.error(e.getMessage());
            throw new RepositoryException("An error occurred while selected subscription: " + e.getMessage());
        }
    }




    public Goods getGoods(long id) throws RepositoryException{

        return mapper.getGoods(id);
    }

    @Override
    public List<Goods> getGoodsByAuthorId(long id) {
        return mapper.getGoodsByAuthorId(id);
    }

    @Override
    public void update(Goods form) {
        mapper.update(form);
    }

    @Override
    public void delete(Long id) {
        mapper.delete(id);
    }

    @Override
    public List<String> imageUrl(long goodsId) {
        List<ImageUrl> images = mapper.getImages(goodsId);
        List<String> url = new LinkedList<String>();
        for(int i=0;i<images.size();i++){
            url.add(images.get(i).getUrl());
        }
        return url;
    }

    @Override
    public String getImageForGoods(long id) {
        return mapper.getImageForGoods(id);
    }

    @Override
    public void addImage(long goodsId, String url) {
        mapper.addImage(goodsId, url);
    }
}
