package it.sevenbits.web.service.goods;

import it.sevenbits.web.domain.DateForm;
import it.sevenbits.web.service.CommonFieldValidator;
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

    private static final Logger LOG = Logger.getLogger(TakeGoodsValidator.class);

    @Autowired
    DealService dealService;

    public HashMap<String, String> validate(final DateForm form, long goodsId) {
        LOG.info("SubscriptionFormValidator started for: " + form.toString());
        HashMap<String, String> errors = new HashMap<String, String>();
        validator.isNotNullOrEmpty(form.getFrom(), errors,"Поле начало", "Начало аренды не может быть пустым");
        validator.isNotNullOrEmpty(form.getTo(), errors, "Поле окончание", "Окончание аренды не может быть пустым");

        validator.isNotEqualStrings(form.getTo(), form.getFrom(), errors, "Поле окончание", "Начало и окончание аренды не могут совпадать");


        validator.isTooEarlyDate(form.getFrom(), form.getTo(), errors, "Поле начало", "Начало аренды не может быть раньше настоящего");
        validator.isTooEarlyDate(form.getTo(), form.getTo(), errors, "Поле окончание", "Окончание аренды не может быть раньше настоящего");

        validator.isEndAfterStart(form.getFrom(), form.getTo(), errors, "", "Окончание аренды должно быть после начала");

        validator.isGoodsAlreadyEngage(form.getFrom(), form.getTo(), goodsId, dealService, errors, "", "К сожалению, это время уже занято");

        validator.isEarlierThenWeek(form.getFrom(), errors, "", "Срок начала аренды не может начинаться позже, чем через неделю после момента бронирования. ");
        validator.isRentTimeLessMonth(form.getFrom(), form.getTo(), errors, "", "Продолжительность аренды может составлять от 1 часа до 1 месяца.");
        validator.isRentTimeMoreHour(form.getFrom(), form.getTo(), errors, "", "Продолжительность аренды может составлять от 1 часа до 1 месяца.");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }

}
