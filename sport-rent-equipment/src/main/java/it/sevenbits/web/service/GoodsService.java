package it.sevenbits.web.service;

import it.sevenbits.core.repository.GoodsRepository;
import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.GoodsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Created by awemath on 7/8/15.
 */
@Service
public class GoodsService {
    @Autowired
    @Qualifier(value="goodsInPostgreSQLrepository")
    private GoodsRepository repository;
    public void save(final GoodsForm form) throws GoodsException {
        final Goods goods = new Goods();
        goods.setTitle(form.getTitle());
        goods.setAuthor(form.getAuthor());
        goods.setAuthorPhone(form.getAuthorPhone());
        goods.setDescription(form.getDescription());
        goods.setPledge(form.getPledge());
        goods.setPricePerHour(Double.valueOf(form.getPricePerHour()));
        goods.setPricePerDay(Double.valueOf(form.getPricePerDay()));
        goods.setPricePerWeek(Double.valueOf(form.getPricePerWeek()));
        try {
            repository.save(goods);
        } catch (Exception e) {
            throw new GoodsException("An error occurred while saving subscription: " + e.getMessage(), e);
        }
    }

    public List<Goods> findAll() throws GoodsException {
        try {
            List<Goods> goods = repository.findAll();
            goods.sort(new Comparator<Goods>() {
                @Override
                public int compare(Goods o1, Goods o2) {
                    return o1.getId() > o1.getId() ? 1 : -1;
                }
            });
            return goods;
        } catch (Exception e) {
            throw new GoodsException("An error occurred while retrieving all goods: " + e.getMessage(), e);
        }

    }

    public Goods getGoods(long id) throws GoodsException{
        Goods goods;
        try{
            goods = repository.getGoods(id);
        } catch (RepositoryException e) {
            throw new GoodsException("Ann error occurred while retrieving one goods with id "+id+": "+e.getMessage(), e);
        }
        return goods;
    }

}
