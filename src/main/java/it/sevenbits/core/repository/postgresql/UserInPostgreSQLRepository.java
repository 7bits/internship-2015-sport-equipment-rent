package it.sevenbits.core.repository.postgresql;

import it.sevenbits.core.mappers.UserMapper;
import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.core.repository.UserRepository;
import it.sevenbits.domain.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by awemath on 7/14/15.
 */
@Repository
@Qualifier(value = "userInPostgreSQLrepository")
public class UserInPostgreSQLRepository implements UserRepository {

    @Autowired
    private UserMapper mapper;

    private Logger logger = Logger.getLogger(UserInPostgreSQLRepository.class);

    @Override
    public void save(final User user) throws RepositoryException {
        try {
            mapper.save(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared on saving user", e);
        }
    }

    @Override
    public User getUser(final String email) throws RepositoryException {
        try {
            return mapper.getUser(email);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared while saving deals", e);
        }
    }

    @Override
    public User getUserById(final long id) throws RepositoryException {
        try {
            return mapper.getUserById(id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared on getting user by id", e);
        }
    }

    @Override
    public int getCountOfUsersWithThatEmail(final String email) throws RepositoryException {
        try {
            return mapper.getCountOfUserWithThatEmail(email);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared on getting users count with email", e);
        }
    }

    @Override
    public void update(final User user) throws RepositoryException {
        try {
            mapper.update(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared on updating user", e);
        }
    }

    @Override
    public void updatePass(final User user) throws RepositoryException {
        try {
            mapper.updatePass(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RepositoryException("An error appeared on updating user`s password", e);
        }
    }
}
