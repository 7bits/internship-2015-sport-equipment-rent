package it.sevenbits.service.exceptions;


/**
 * Created by awemath on 7/8/15.
 */
public class GoodsException extends Exception {
    public GoodsException(
            final String s,
            final Exception e) {
        super(s, e);
    }
}