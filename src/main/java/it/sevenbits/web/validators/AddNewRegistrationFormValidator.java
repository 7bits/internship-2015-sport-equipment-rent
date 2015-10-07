package it.sevenbits.web.validators;

import it.sevenbits.service.UserService;
import it.sevenbits.service.exceptions.UserServiceException;
import it.sevenbits.service.validators.CommonFieldValidator;
import it.sevenbits.web.forms.RegistrationForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by awemath on 7/17/15.
 */
@Service
public class AddNewRegistrationFormValidator {
    @Autowired
    private CommonFieldValidator validator;


    @Autowired
    private UserService userService;
    private static final Logger LOG = Logger.getLogger(AddNewGoodsFormValidator.class);

    @Autowired
    MessageSource messageSource;

    public HashMap<String, String> validate(final RegistrationForm form) throws UserServiceException {
        LOG.info("SubscriptionFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<String, String>();
        Locale locale = LocaleContextHolder.getLocale();
        validator.isNotNullOrEmpty(form.geteMail(), errors, "Поле email",
                messageSource.getMessage("message.field.email", null, locale) + " " +
                        messageSource.getMessage("message.error.empty", null, locale));
        validator.isNotNullOrEmpty(String.valueOf(form.getFirstName()), errors, "Поле имя",
                messageSource.getMessage("message.field.firstName", null, locale) + " " +
                        messageSource.getMessage("message.error.empty", null, locale));

        validator.isNotNullOrEmpty(String.valueOf(form.getPassword()), errors, "Поле пароль",
                messageSource.getMessage("message.field.pass", null, locale) + " " +
                        messageSource.getMessage("message.error.empty", null, locale));

        validator.isEmail(form.geteMail(), errors, "Поле email",
                messageSource.getMessage("message.field.email", null, locale) + " " +
                        messageSource.getMessage("message.error.incorrect", null, locale));

        validator.shorterThan(form.getFirstName(), 50, errors, "Поле имя",
                messageSource.getMessage("message.field.firstName", null, locale) + " " +
                        messageSource.getMessage("message.error.shorterThan", new Object[]{50}, locale));

        validator.shorterThan(form.getPassword(), 100, errors, "Поле пароль",
                messageSource.getMessage("message.field.pass", null, locale) + " " +
                        messageSource.getMessage("message.error.shorterThan", new Object[]{100}, locale));

        validator.isUniqueEmail(form.geteMail(), userService, errors, "Поле emai",
                messageSource.getMessage("message.field.message.error.userAlreadyExist", null, locale));

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}

