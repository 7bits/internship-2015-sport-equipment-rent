package it.sevenbits.web.validators;

import it.sevenbits.service.validators.CommonFieldValidator;
import it.sevenbits.web.forms.GoodsForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.LocaleResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by awemath on 7/9/15.
 */

@Service
public class AddNewGoodsFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(AddNewGoodsFormValidator.class);

    @Autowired
    MessageSource messageSource;

    @Autowired
    LocaleResolver localeResolver;

    public HashMap<String, String> validate(final GoodsForm form) {
        LOG.info("GoodsFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        validator.isNotNullOrEmpty(form.getTitle(), errors, "",
                messageSource.getMessage("message.field.title", null, locale) + " " +
                        messageSource.getMessage("message.error.empty", null, locale));
        validator.isNotNullOrEmpty(String.valueOf(form.getPricePerHour()), errors, "Поле цена за час",
                messageSource.getMessage("message.field.pricePerDay", null, locale) + " " +
                        messageSource.getMessage("message.error.empty", null, locale));

        validator.isNotNullOrEmpty(String.valueOf(form.getPricePerDay()), errors, "Поле цена за день",
                messageSource.getMessage("message.field.pricePerDay", null, locale) + " " +
                        messageSource.getMessage("message.error.empty", null, locale));

        validator.isNotNullOrEmpty(String.valueOf(form.getPricePerWeek()), errors, "Поле цена за неделю",
                messageSource.getMessage("message.field.pricePerWeek", null, locale) + " " +
                        messageSource.getMessage("message.error.empty", null, locale));

        validator.shorterThan(form.getTitle(), 255, errors, "Поле заголовок",
                messageSource.getMessage("message.field.title", null, locale) + " " +
                        messageSource.getMessage("message.error.shorterThan", new Object[]{255}, locale));
        validator.shorterThan(form.getDescription(), 2040, errors, "Поле описание",
                messageSource.getMessage("message.field.title", null, locale) + " " +
                        messageSource.getMessage("message.error.shorterThan", new Object[]{2048}, locale));
        validator.shorterThan(form.getPricePerHour(), 10, errors, "Поле цена за час",
                messageSource.getMessage("message.field.pricePerHour", null, locale) + " " +
                        messageSource.getMessage("message.error.shorterThan", new Object[]{10}, locale));
        validator.shorterThan(form.getPricePerDay(), 10, errors, "Поле цена за день",
                messageSource.getMessage("message.field.pricePerDay", null, locale) + " " +
                        messageSource.getMessage("message.error.shorterThan", new Object[]{10}, locale));
        validator.shorterThan(form.getPricePerWeek(), 10, errors, "Поле цена за неделю",
                messageSource.getMessage("message.field.pricePerWeek", null, locale) + " " +
                        messageSource.getMessage("message.error.shorterThan", new Object[]{10}, locale));

        validator.isNum(form.getPricePerHour(), errors, "Поле цена за час",
                messageSource.getMessage("message.field.pricePerHour", null, locale) + " " +
                        messageSource.getMessage("message.error.notNum", null, locale));
        validator.isNum(form.getPricePerDay(), errors, "Поле цена за день",
                messageSource.getMessage("message.field.pricePerDay", null, locale) + " " +
                        messageSource.getMessage("message.error.notNum", null, locale));
        validator.isNum(form.getPricePerWeek(), errors, "Поле цена за неделю",
                messageSource.getMessage("message.field.pricePerWeek", null, locale) + " " +
                        messageSource.getMessage("message.error.notNum", null, locale));

        validator.isPositiveNum(String.valueOf(form.getPricePerHour()), errors, "Поле цена за час",
                messageSource.getMessage("message.field.pricePerHour", null, locale) + " " +
                        messageSource.getMessage("message.error.notPositive", null, locale));
        validator.isPositiveNum(String.valueOf(form.getPricePerDay()), errors, "Поле цена за день",
                messageSource.getMessage("message.field.pricePerDay", null, locale) + " " +
                        messageSource.getMessage("message.error.notPositive", null, locale));
        validator.isPositiveNum(String.valueOf(form.getPricePerWeek()), errors, "Поле цена за неделю",
                messageSource.getMessage("message.field.pricePerWeek", null, locale) + " " +
                        messageSource.getMessage("message.error.notPositive", null, locale));

        validator.isImages(form.getImageUrl(), errors, "Поле цена за неделю",
                messageSource.getMessage("message.field.pricePerHour", null, locale) + " " +
                        messageSource.getMessage("message.error.notImage", null, locale));

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}
