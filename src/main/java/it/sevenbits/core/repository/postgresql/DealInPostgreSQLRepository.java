package it.sevenbits.core.repository.postgresql;

import it.sevenbits.core.mappers.DealMapper;
import it.sevenbits.core.repository.DealRepository;
import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.domain.Deal;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by awemath on 7/21/15.
 */

@Repository
@Qualifier(value = "dealInPostgreSQLRepository")
public class DealInPostgreSQLRepository implements DealRepository {
    @Autowired
    private DealMapper mapper;

    private Logger logger = Logger.getLogger(DealInPostgreSQLRepository.class);

    @Override
    public void save(final Deal deal) throws RepositoryException {
        try {
            mapper.save(deal);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared while saving deals", e);
        }
    }

    public Deal getDeal(final long dealId) throws RepositoryException {
        try {
            return mapper.getDeal(dealId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared while getting deals", e);
        }
    }

    @Override
    public void update(final Deal deal) throws RepositoryException {
        try {
            mapper.update(deal);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared while updating deals", e);
        }
    }

    @Override
    public long getId(final Deal deal) throws RepositoryException {
        try {
            return mapper.getId(deal);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared while getting deals id", e);
        }
    }

    @Override
    public boolean isExist(final Deal deal) throws RepositoryException {
        try {
            return mapper.isExist(deal) != 0;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared while checking deals existing", e);
        }
    }

    @Override
    public void deleteAllOnGoods(final long goodsId) throws RepositoryException {
        try {
            mapper.deleteAllOnGoods(goodsId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared while deleting all delas on goods", e);
        }
    }

    @Override
    public void updateRealStartDate(final long dealId) throws RepositoryException {
        try {
            mapper.updateRealStartDate(dealId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared while updating real start date on deal", e);
        }
    }

    @Override
    public void updateRealEndDate(final long dealId) throws RepositoryException {
        try {
            mapper.updateRealEndDate(dealId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared while updating real end date on deal", e);
        }
    }

    @Override
    public List<Deal> getOpenWithId(final long goodsId) throws RepositoryException {
        try {
            return mapper.getOpenWithId(goodsId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared while getting open deals", e);
        }
    }

    @Override
    public List<Deal> getDealsOfUser(final Long id) throws RepositoryException {
        try {
            return mapper.getDealsOfUser(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared while getting users deals", e);
        }
    }

}