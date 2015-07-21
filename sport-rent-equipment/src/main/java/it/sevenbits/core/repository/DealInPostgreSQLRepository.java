package it.sevenbits.core.repository;

import it.sevenbits.core.mappers.DealMapper;
import it.sevenbits.web.domain.Deal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

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
}
