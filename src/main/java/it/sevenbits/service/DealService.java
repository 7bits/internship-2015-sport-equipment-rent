package it.sevenbits.service;

import it.sevenbits.core.repository.dealrepository.DealRepository;
import it.sevenbits.web.domain.Deal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by awemath on 7/21/15.
 */
@Service
public class DealService {
    @Autowired
    @Qualifier(value="dealInPostgreSQLRepository")
    private DealRepository repository;

    public void save(Deal deal){
        repository.save(deal);
    }

    public Deal getDeal(long dealId) {
        return repository.getDeal(dealId);
    }

    public long getId(Deal deal){
        return repository.getId(deal);
    }


    public void update(Deal deal){
        repository.update(deal);
    }


    public boolean isExist(Deal deal) {
        return repository.isExist(deal);
    }

    public void deleteAllOnGoods(long goodsId) {
        repository.deleteAllOnGoods(goodsId);
    }

    public void updateRealStartDate(long dealId) {
        repository.updateRealStartDate(dealId);
    }

    public void updateRealEndDate(long dealId) {
        repository.updateRealEndDate(dealId);
    }

    public List<Deal> getOpenWithId(long goodsId) {
        return repository.getOpenWithId(goodsId);
    }

    public List<Deal> getDealsOfUser(Long id) {
        return repository.getDealsOfUser(id);
    }
}
