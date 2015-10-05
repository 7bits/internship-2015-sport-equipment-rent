package it.sevenbits.service;

/**
 * Created by awemath on 7/14/15.
 */

import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.core.repository.UserRepository;
import it.sevenbits.domain.User;
import it.sevenbits.service.exceptions.ServiceException;
import it.sevenbits.service.exceptions.UserServiceException;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    @Qualifier(value = "userInPostgreSQLrepository")
    private UserRepository repository;



    @Value("${resources.default-users-avatar}")
    private String defaultUserAvatar;

    private Logger logger = Logger.getLogger(UserService.class);

    public User getUser(final long id) throws ServiceException {
        User user;
        try {
            user = repository.getUserById(id);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    public User getUser(final String email) throws ServiceException {
        User user;
        try {
            user = repository.getUser(email);
        } catch (RepositoryException e) {
            throw new ServiceException(e);
        }
        return user;
    }

    public int getCountOfUsersWithEmail(final String email) throws UserServiceException {
        try {
            return repository.getCountOfUsersWithThatEmail(email);
        } catch (RepositoryException e) {
            throw new UserServiceException("An error appeared on getting users count with email", e);
        }
    }

    public void save(final User user) throws UserServiceException {
        user.setImageUrl(defaultUserAvatar);

        try {
            repository.save(user);
        } catch (Exception e) {
            throw new UserServiceException("An error occurred while saving subscription: " + e.getMessage(), e);
        }
    }

    public void update(final User user) {
        try {
            repository.update(user);
        } catch (RepositoryException e) {
            logger.error("An error appeared on updating user", e);
        }
    }

    public void updatePass(final User user) {
        user.setPass(BCrypt.hashpw(user.getPass(), BCrypt.gensalt()));
        try {
            repository.updatePass(user);
        } catch (RepositoryException e) {
            logger.error("An error appeared on updating users password", e);

        }
    }
}