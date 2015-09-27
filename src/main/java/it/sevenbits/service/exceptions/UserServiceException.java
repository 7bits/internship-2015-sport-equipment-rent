package it.sevenbits.service.exceptions;

/**
 * Created by awemath on 9/27/15.
 */
public class UserServiceException extends Exception {
    public UserServiceException(String message, Exception e) {
        super(message, e);
    }
}
