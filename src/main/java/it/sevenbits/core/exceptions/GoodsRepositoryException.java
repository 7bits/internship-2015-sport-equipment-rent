package it.sevenbits.core.exceptions;

/**
 * Created by awemath on 9/25/15.
 */
public class GoodsRepositoryException extends Exception {
    public GoodsRepositoryException(final String message, final Exception e) {
        super(message, e);
    }
    public GoodsRepositoryException(final String message) {
        super(message);
    }
}
