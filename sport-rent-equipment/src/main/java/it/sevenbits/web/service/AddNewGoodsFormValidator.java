package it.sevenbits.web.service;

import it.sevenbits.web.domain.GoodsForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
/**
 * Created by awemath on 7/9/15.
 */





@Service
public class AddNewGoodsFormValidator {
    @Autowired
    private CommonFieldValidator validator;

    private static final Logger LOG = Logger.getLogger(AddNewGoodsFormValidator.class);

    public HashMap<String, String> validate(final GoodsForm form) {
        LOG.info("SubscriptionFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<String, String>();

        validator.isNotNullOrEmpty(form.getAuthor(), errors, "name", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getAuthorPhone(), errors, "authorPhone", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getTitle(), errors, "title", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(String.valueOf(form.getPricePerHour()), errors, "pricePerHour", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(String.valueOf(form.getPricePerDay()), errors, "pricePerDay", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(String.valueOf(form.getPricePerWeek()), errors, "pricePerWeek", "Поле не должно быть пустым");
        validator.isNotNullOrEmpty(form.getPledge(), errors, "pledge", "Поле не должно быть пустым");

        validator.shorterThan(form.getAuthor(), 50, errors, "author", "Поле должно быть короче чем 50 символов");
        validator.shorterThan(form.getAuthorPhone(), 15, errors, "authorPhone", "Поле должно быть короче чем 15 символов");

        validator.isPositiveNum(String.valueOf(form.getPricePerHour()), errors, "pricePerHour", "Field should contain only numerics");
        validator.isPositiveNum(String.valueOf(form.getPricePerDay()), errors, "pricePerDay", "Field should contain only numerics");
        validator.isPositiveNum(String.valueOf(form.getPricePerWeek()), errors, "pricePerWeek", "Field should contain only numerics");
        validator.isPositiveNum(String.valueOf(form.getAuthorPhone()), errors, "pricePerWeek", "Field should contain only numerics");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}
