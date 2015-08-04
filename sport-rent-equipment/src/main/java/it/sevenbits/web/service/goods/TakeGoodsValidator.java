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
        validator.isNotNullOrEmpty(form.getFrom(), errors, "Поле заголовок", "Поле заголовок не может быть пустым");
        validator.isNotNullOrEmpty(form.getTo(), errors, "", "");

        validator.isNotEqualStrings(form.getTo(), form.getFrom(), errors, "", "");


        validator.isTooEarlyDate(form.getFrom(), form.getTo(), errors, "", "");
        validator.isTooEarlyDate(form.getTo(), form.getTo(), errors, "", "");

        validator.isEndAfterStart(form.getFrom(), form.getTo(), errors, "", "");

        validator.isGoodsAlreadyEngage(form.getFrom(), form.getTo(), goodsId, dealService, errors, "", "");

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            LOG.info(String.format("Error found: Filed=%s, Error=%s",
                    entry.getKey(), entry.getValue()));
        }

        return errors;
    }

}
