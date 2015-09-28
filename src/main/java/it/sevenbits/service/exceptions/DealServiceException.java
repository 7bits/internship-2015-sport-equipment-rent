package it.sevenbits.service.exceptions;

/**
 * Created by awemath on 9/27/15.
 */
public class DealServiceException extends Exception {
    public DealServiceException(final String message, final Exception e) {
        super(message, e);
    }
    public DealServiceException(final String message) {
        super(message);
    }
}
