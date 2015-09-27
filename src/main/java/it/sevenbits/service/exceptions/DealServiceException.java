package it.sevenbits.service.exceptions;

/**
 * Created by awemath on 9/27/15.
 */
public class DealServiceException extends Exception {
    public DealServiceException (String message, Exception e){
        super(message, e);
    }
    public DealServiceException (String message){
        super(message);
    }
}
