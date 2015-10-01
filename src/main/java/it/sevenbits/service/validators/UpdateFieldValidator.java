package it.sevenbits.service.validators;

import it.sevenbits.service.UserService;
import it.sevenbits.web.forms.UpdateUserForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by awemath on 8/7/15.
 */
@Service
public class UpdateFieldValidator {
    @Autowired
    private CommonFieldValidator validator;


    @Autowired
    private UserService userService;
    private static final Logger LOG = Logger.getLogger(UpdateFieldValidator.class);
    public HashMap<String, String> validate(final UpdateUserForm form) {
        LOG.info("SubscriptionFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<String, String>();
        validator.isNotNullOrEmpty(String.valueOf(form.getFirstName()), errors, "Поле имя",
                "Поле имя не может быть пустым");
        validator.shorterThan(form.getFirstName(), 50, errors, "Поле имя", "Поле имя должно быть короче 50 символов");
        validator.isImage(form.getImageUrl(), errors, "", "");
        validator.shorterThan(form.getPhone(), 50, errors, "Поле телефон", "Поле телефон должно быть короче 50 символов");
        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}
