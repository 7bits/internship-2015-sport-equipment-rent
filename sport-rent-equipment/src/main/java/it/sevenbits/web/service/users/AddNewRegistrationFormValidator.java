package it.sevenbits.web.service.users;

import it.sevenbits.web.domain.RegistrationForm;
import it.sevenbits.web.service.goods.AddNewGoodsFormValidator;
import it.sevenbits.web.service.CommonFieldValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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


    public HashMap<String, String> validate(final RegistrationForm form) {
        LOG.info("SubscriptionFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<String, String>();
        validator.isNotNullOrEmpty(form.geteMail(), errors, "Поле email", "Поле email не может быть пустым");
        validator.isNotNullOrEmpty(String.valueOf(form.getFirstName()), errors, "Поле имя", "Поле имя не может быть пустым");
        validator.isNotNullOrEmpty(String.valueOf(form.getPassword()), errors, "Поле пароль", "Поле пароль не может быть пустым");

        validator.isEmail(form.geteMail(), errors, "Поле email", "Введите существующий email");

        validator.shorterThan(form.getFirstName(), 50, errors, "Поле имя", "Поле имя должно быть короче 50 символов");
        validator.shorterThan(form.getPassword(), 100, errors, "Поле пароль", "Пароль должен быть короче 100 символов");

        validator.isUniqueEmail(form.geteMail(), userService, errors, "Поле emai", "Пользователь с таким email уже существует");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }


    }
