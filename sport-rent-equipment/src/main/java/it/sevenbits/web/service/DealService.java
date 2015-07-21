package it.sevenbits.web.service;

import it.sevenbits.core.repository.DealRepository;
import it.sevenbits.web.domain.Deal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by awemath on 7/21/15.
 */
@Service
public class DealService {
    @Autowired
    @Qualifier(value="dealInPostgreSQLRepository")
    private DealRepository repository;
    void save(Deal deal){
        repository.save(deal);
    }

    public Deal getDeal() {

    }
}
