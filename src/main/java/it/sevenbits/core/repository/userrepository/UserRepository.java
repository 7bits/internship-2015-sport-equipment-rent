package it.sevenbits.core.repository.userrepository;

import it.sevenbits.core.repository.RepositoryException;
import it.sevenbits.domain.User;

/**
 * Created by awemath on 7/14/15.
 */
public interface UserRepository {
    void save(final User user) throws RepositoryException;
    User getUser(final String email) throws RepositoryException;
    User getUserById(final long id) throws RepositoryException;
    int getCountOfUsersWithThatEmail(String email);

    void update(User user);

    void updatePass(User user);
}
