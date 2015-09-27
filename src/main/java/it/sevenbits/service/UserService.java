package it.sevenbits.service;

/**
 * Created by awemath on 7/14/15.
 */

import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.service.exceptions.GoodsException;
import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.web.forms.RegistrationForm;
import it.sevenbits.domain.User;
import org.mindrot.jbcrypt.BCrypt;
import it.sevenbits.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    @Qualifier(value="userInPostgreSQLrepository")
    private UserRepository repository;
    
    @Value("${resources.default-users-avatar}")
    private String defaultUserAvatar;

    public User getUser(long id) throws UserServiceException {
        User user;
        try{
            user = repository.getUserById(id);
        } catch (RepositoryException e) {
            throw new UserServiceException("Ann error occurred while retrieving one goods with id "+id+": "+e.getMessage(), e);
        }
        return user;
    }

    public User getUser(String email) throws UserServiceException {
        User user;
        try {
            user = repository.getUser(email);
        } catch (RepositoryException e) {
            throw new UserServiceException("Ann error occurred while retrieving one goods with id: "+e.getMessage(), e);
        } catch(NullPointerException e){
            throw new UserServiceException("null pointer exception", e);
        }
        return user;
    }

    public int getCountOfUsersWithEmail(String email){
        return repository.getCountOfUsersWithThatEmail(email);
    }

    public void save(RegistrationForm form) throws UserServiceException {
        final User user = new User();
        user.setEmail(form.geteMail());
        user.setFirstName(form.getFirstName());
        user.setPass(BCrypt.hashpw(form.getPassword(), BCrypt.gensalt()));
        user.setImageUrl(defaultUserAvatar);
        try {
            repository.save(user);
        } catch (Exception e) {
            throw new UserServiceException("An error occurred while saving subscription: " + e.getMessage(), e);
        }
    }

    public void update(User user) {
        repository.update(user);
    }

    public void updatePass(User user) {
        user.setPass(BCrypt.hashpw(user.getPass(), BCrypt.gensalt()));
        repository.updatePass(user);
    }
}