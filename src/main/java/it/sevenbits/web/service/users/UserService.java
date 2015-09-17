package it.sevenbits.web.service.users;

/**
 * Created by awemath on 7/14/15.
 */

import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.core.repository.userrepository.UserRepository;
import it.sevenbits.web.domain.RegistrationForm;
import it.sevenbits.web.domain.User;
import it.sevenbits.web.service.goods.GoodsException;
import org.mindrot.jbcrypt.BCrypt;
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


    @Value("${default-users-avatar}")
    private String defaultUserAvatar;

    public User getUser(long id) throws GoodsException {
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

    public int getCountOfUsersWithThatEmail(String email){
        return repository.getCountOfUsersWithThatEmail(email);
    }

    public void save(RegistrationForm form) throws GoodsException {
        final User user = new User();
        user.setEmail(form.geteMail());
        user.setFirstName(form.getFirstName());
        user.setPass(BCrypt.hashpw(form.getPassword(), BCrypt.gensalt()));
        user.setImageUrl(defaultUserAvatar);
        try {
            repository.save(user);
        } catch (Exception e) {
            throw new GoodsException("An error occurred while saving subscription: " + e.getMessage(), e);
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