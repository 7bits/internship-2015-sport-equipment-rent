package it.sevenbits.service.validators;

import it.sevenbits.service.DealService;
import it.sevenbits.service.exceptions.ServiceException;
import it.sevenbits.web.forms.DateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by awemath on 8/3/15.
 */

@Service
public class TakeGoodsValidator {
    @Autowired
    private CommonFieldValidator validator;

    @Autowired
    private DealService dealService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;


    public HashMap<String, String> validate(
            final DateForm form,
            final long goodsId) throws ServiceException {
        Locale locale = LocaleContextHolder.getLocale();
        HashMap<String, String> errors = new HashMap<String, String>();
        validator.isNotNullOrEmpty(form.getFrom(), errors, "from",
                messageSource.getMessage("message.field.rentStart", null, locale) + " " +
                        messageSource.getMessage("message.error.empty", null, locale));
        validator.isNotNullOrEmpty(form.getTo(), errors, "to",
                messageSource.getMessage("message.field.rentEnd", null, locale) + " " +
                        messageSource.getMessage("message.error.empty", null, locale));

        validator.isTooEarlyDate(form.getFrom(), errors, "from",
                messageSource.getMessage("message.field.rentStart", null, locale) + " " +
                        messageSource.getMessage("message.error.early", null, locale));

        validator.isTooEarlyDate(form.getTo(), errors, "to",
                messageSource.getMessage("message.field.rentEnd", null, locale) + " " +
                        messageSource.getMessage("message.error.early", null, locale));

        validator.isEndAfterStart(form.getFrom(), form.getTo(), errors, "",
                messageSource.getMessage("message.error.endAfterStart", null, locale));

        validator.isGoodsAlreadyEngage(form.getFrom(), form.getTo(), goodsId, dealService, errors, "",
                messageSource.getMessage("message.error.timeAlreadyEngage", null, locale));

        validator.isEarlierThenWeek(form.getFrom(), errors, "from",
                messageSource.getMessage("message.error.startIsTooLate", null, locale));

        validator.isRentTimeLessMonth(form.getFrom(), form.getTo(), errors, "to",
                messageSource.getMessage("message.error.badDuration", null, locale));
        validator.isRentTimeMoreHour(form.getFrom(), form.getTo(), errors, "to",
                messageSource.getMessage("message.error.badDuration", null, locale));
        return errors;
    }

}
