package it.sevenbits.service.validators;

import it.sevenbits.service.DealService;
import it.sevenbits.service.exceptions.ServiceException;
import it.sevenbits.web.forms.DateForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by awemath on 8/3/15.
 */

@Service
public class TakeGoodsValidator {
    @Autowired
    private CommonFieldValidator validator;

    private Logger logger = Logger.getLogger(TakeGoodsValidator.class);

    @Autowired
    private DealService dealService;

    public HashMap<String, String> validate(final DateForm form,
                                            final long goodsId) throws ServiceException {

        logger.info("SubscriptionFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<String, String>();
        validator.isNotNullOrEmpty(form.getFrom(), errors, "Поле начало", "Начало аренды не может быть пустым!");
        validator.isNotNullOrEmpty(form.getTo(), errors, "Поле окончание", "Окончание аренды не может быть пустым!");

        validator.isNotEqualStrings(form.getTo(), form.getFrom(), errors, "Поле окончание", "Начало и окончание аренды не могут совпадать!");


        validator.isTooEarlyDate(form.getFrom(), form.getTo(), errors, "Поле начало", "Начало аренды не может быть раньше настоящего момента времени!");
        validator.isTooEarlyDate(form.getTo(), form.getTo(), errors, "Поле окончание", "Окончание аренды не может быть раньше настоящего момента времени!");

        validator.isEndAfterStart(form.getFrom(), form.getTo(), errors, "", "Окончание аренды должно быть после начала аренды!");

        validator.isGoodsAlreadyEngage(form.getFrom(), form.getTo(), goodsId, dealService, errors, "", "К сожалению, это время уже занято!");

        validator.isEarlierThenWeek(form.getFrom(), errors, "", "Срок начала аренды не может начинаться позже, чем через неделю после момента бронирования. ");
        validator.isRentTimeLessMonth(form.getFrom(), form.getTo(), errors, "", "Продолжительность аренды может составлять от 1 часа до 1 месяца.");
        validator.isRentTimeMoreHour(form.getFrom(), form.getTo(), errors, "", "Продолжительность аренды может составлять от 1 часа до 1 месяца.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            logger.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }

}
