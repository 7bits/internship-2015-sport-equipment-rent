package it.sevenbits.web.validators;

import it.sevenbits.service.validators.CommonFieldValidator;
import it.sevenbits.web.forms.GoodsForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
        validator.isNotNullOrEmpty(form.getTitle(), errors, "Поле заголовок", "Поле заголовок не может быть пустым");
        validator.isNotNullOrEmpty(String.valueOf(form.getPricePerHour()),
                errors,
                "Поле цена за час",
                "Поле цена за час не может быть пустым");
        validator.isNotNullOrEmpty(String.valueOf(form.getPricePerDay()),
                errors,
                "Поле цена за день",
                "Поле цена за день не может быть пустым");
        validator.isNotNullOrEmpty(String.valueOf(form.getPricePerWeek()), errors, "Поле цена за неделю", "Поле цена за неделю не может быть пустым");

        validator.shorterThan(form.getTitle(), 255, errors, "Поле заголовок", "Поле заголовок должно быть короче чем 255 символов");
        validator.shorterThan(form.getDescription(), 2040, errors, "Поле описание", "Поле описание должно быть короче чем 2048 символов");
        validator.shorterThan(form.getPricePerHour(), 10, errors, "Поле цена за час", "Поле цена за час должно быть короче чем 10 символов");
        validator.shorterThan(form.getPricePerDay(), 10, errors, "Поле цена за день", "Поле цена за день должно быть короче чем 10 символов");
        validator.shorterThan(form.getPricePerWeek(), 10, errors, "Поле цена за неделю", "Поле цена за неделю должно быть короче чем 10 символов");
        validator.shorterThan(form.getPledge(), 50, errors, "Поле залог", "Поле залог должно быть короче чем 50 символов");

        validator.isNum(form.getPricePerHour(), errors, "Поле цена за час", "Поле цена за час должно содержать только цифры");
        validator.isNum(form.getPricePerDay(), errors, "Поле цена за день", "Поле цена за день должно содержать только цифры");
        validator.isNum(form.getPricePerWeek(), errors, "Поле цена за неделю", "Поле цена за неделю должно содержать только цифры");

        validator.isPositiveNum(String.valueOf(form.getPricePerHour()), errors, "Поле цена за час", "Поле цена за час должно содержать положительное значение");
        validator.isPositiveNum(String.valueOf(form.getPricePerDay()), errors, "Поле цена за день", "Поле цена за день должно содержать положительное значение");
        validator.isPositiveNum(String.valueOf(form.getPricePerWeek()), errors, "Поле цена за неделю", "Поле цена за неделю должно содержать положительное значение");

        validator.isImages(form.getImageUrl(), errors, "Поле цена за неделю", "Поле цена за неделю должно содержать положительное значение");
        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }
}
