package it.sevenbits.core.repository;

import it.sevenbits.web.domain.User;

/**
 * Created by awemath on 7/14/15.
 */
public interface UserRepository {
    void save(final User user) throws RepositoryException;
    User getUser(final String email) throws RepositoryException;
    User getUserById(final long id) throws RepositoryException;
}
