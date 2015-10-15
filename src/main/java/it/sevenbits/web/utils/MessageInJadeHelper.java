package it.sevenbits.web.utils;

import com.domingosuarez.boot.autoconfigure.jade4j.JadeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * Created by awemath on 10/15/15.
 */
@JadeHelper("jadeMessage")
public class MessageInJadeHelper {

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String messageId){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageId, null, locale);
    }
}