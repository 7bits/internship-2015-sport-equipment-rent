package it.sevenbits.core.exceptions;

/**
 * Created by awemath on 9/25/15.
 */
public class UserRepositoryException extends Exception {
    public UserRepositoryException(String message, Exception e) {
        super(message, e);
    }

}
