package it.sevenbits.core.exceptions;

/**
 * Created by awemath on 9/25/15.
 */
public class GoodsRepositoryException extends Exception {
    public GoodsRepositoryException(String message, Exception e) {
        super(message, e);
    }
}
