package it.sevenbits.service.exceptions;

/**
 * Created by awemath on 9/27/15.
 */
public class UserServiceException extends Exception {
    public UserServiceException(
            final String message,
            final Exception e) {
        super(message, e);
    }
}
