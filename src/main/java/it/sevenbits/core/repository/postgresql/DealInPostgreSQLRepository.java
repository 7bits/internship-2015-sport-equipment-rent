package it.sevenbits.core.repository.postgresql;

import it.sevenbits.core.mappers.DealMapper;
import it.sevenbits.core.repository.DealRepository;
import it.sevenbits.domain.Deal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by awemath on 7/21/15.
 */

@Repository
@Qualifier(value="dealInPostgreSQLRepository")
public class DealInPostgreSQLRepository implements DealRepository {
    @Autowired
    DealMapper mapper;
    @Override
    public void save(Deal deal) {
        mapper.save(deal);
    }

    public Deal getDeal(long dealId) {
        return mapper.getDeal(dealId);
    }

    @Override
    public void update(Deal deal) {
        mapper.update(deal);
    }

    @Override
    public long getId(Deal deal) {
        return mapper.getId(deal);
    }

    @Override
    public boolean isExist(Deal deal) {
        return mapper.isExist(deal)!=0;
    }

    @Override
    public void deleteAllOnGoods(long goodsId) {
        mapper.deleteAllOnGoods(goodsId);
    }

    @Override
    public void updateRealStartDate(long dealId) {
        mapper.updateRealStartDate(dealId);
    }

    @Override
    public void updateRealEndDate(long dealId) {
        mapper.updateRealEndDate(dealId);
    }

    @Override
    public List<Deal> getOpenWithId(long goodsId) {
        return mapper.getOpenWithId(goodsId);
    }

    @Override
    public List<Deal> getDealsOfUser(Long id) {
        return mapper.getDealsOfUser(id);
    }

}
