package it.sevenbits.web.service.users;

import it.sevenbits.web.domain.UpdateUserForm;
import it.sevenbits.web.service.CommonFieldValidator;
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
        validator.isNotNullOrEmpty(String.valueOf(form.getFirstName()), errors, "Поле имя", "Поле имя не может быть пустым");
        validator.shorterThan(form.getFirstName(), 50, errors, "Поле имя", "Поле имя должно быть короче 50 символов");
        validator.isImage(form.getImageUrl(), errors, "", "");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}
