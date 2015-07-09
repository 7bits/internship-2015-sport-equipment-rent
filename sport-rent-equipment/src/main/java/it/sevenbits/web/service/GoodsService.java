package it.sevenbits.web.service;

import it.sevenbits.core.repository.GoodsRepository;
import it.sevenbits.web.domain.Goods;
import it.sevenbits.web.domain.GoodsForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
        goods.setPricePerHour(form.getPricePerHour());
        goods.setPricePerDay(form.getPricePerDay());
        goods.setPricePerWeek(form.getPricePerWeek());
        try {
            repository.save(goods);
        } catch (Exception e) {
            throw new GoodsException("An error occurred while saving subscription: " + e.getMessage(), e);
        }
    }
}
