package it.sevenbits.service.exceptions;

/**
 * Created by awemath on 10/5/15.
 */
public class ServiceException extends Exception {
    public ServiceException(
            final String message,
            final Exception e) {
        super(message, e);
    }
}
