package it.sevenbits.web.service.goods;


/**
 * Created by awemath on 7/8/15.
 */
public class GoodsException extends Exception {
    public GoodsException(String s, Exception e) {
        super(s, e);
    }
}