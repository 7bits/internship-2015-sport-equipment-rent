package it.sevenbits.core.repository.postgresql;

import it.sevenbits.core.mappers.UserMapper;
import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.core.repository.UserRepository;
import it.sevenbits.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * Created by awemath on 7/14/15.
 */
@Repository
@Qualifier(value="userInPostgreSQLrepository")
public class UserInPostgreSQLRepository implements UserRepository {

    @Autowired
    private UserMapper mapper;
    @Override
    public void save(User user) throws RepositoryException {
        try{
            mapper.save(user);
        }catch (Exception e){
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public User getUser(String email) throws RepositoryException {
        return mapper.getUser(email);
    }

    @Override
    public User getUserById(long id) throws RepositoryException {
        return mapper.getUserById(id);
    }

    @Override
    public int getCountOfUsersWithThatEmail(String email) {
        return mapper.getCountOfUserWithThatEmail(email);
    }

    @Override
    public void update(User user) {
        mapper.update(user);
    }

    @Override
    public void updatePass(User user) {
        mapper.updatePass(user);
    }
}
