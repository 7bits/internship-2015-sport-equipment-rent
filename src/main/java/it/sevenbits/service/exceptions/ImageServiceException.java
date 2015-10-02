package it.sevenbits.service.exceptions;

/**
 * Created by awemath on 9/27/15.
 */
public class ImageServiceException extends Exception {
    public ImageServiceException(String message, Exception e) {
        super(message, e);
    }
}
