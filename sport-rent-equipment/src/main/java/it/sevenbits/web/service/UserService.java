package it.sevenbits.web.service;

/**
 * Created by awemath on 7/14/15.
 */

import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.core.repository.UserRepository;
import it.sevenbits.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    @Qualifier(value="userInPostgreSQLrepository")
    private UserRepository repository;

    public User getUser(long id) throws GoodsException{
        User user;
        try{
            user = repository.getUserById(id);
        } catch (RepositoryException e) {
            throw new GoodsException("Ann error occurred while retrieving one goods with id "+id+": "+e.getMessage(), e);
        }
        return user;
    }

    public User getUser(String email) throws GoodsException {
        User user;
        try {
            user = repository.getUser(email);
        } catch (RepositoryException e) {
            throw new GoodsException("Ann error occurred while retrieving one goods with id: "+e.getMessage(), e);
        } catch(NullPointerException e){
            throw new GoodsException("null pointer exception", e);
        }
        return user;
    }

}
