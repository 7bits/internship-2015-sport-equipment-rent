package it.sevenbits.core.repository;

import it.sevenbits.web.domain.Deal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by awemath on 7/21/15.
 */

@Repository
@Qualifier(value="dealInPostgreSQLRepository")
public class DealInPostgreSQLRepository implements DealRepository {
    @Override
    public void save(Deal deal) {

    }
}
