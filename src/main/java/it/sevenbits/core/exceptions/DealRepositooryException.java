package it.sevenbits.core.exceptions;

/**
 * Created by awemath on 9/25/15.
 */
public class DealRepositooryException extends Exception {
    public DealRepositooryException(String message, Exception e) {
        super(message, e);
    }
}
