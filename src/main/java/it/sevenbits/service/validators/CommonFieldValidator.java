package it.sevenbits.service.validators;

/**
 * Created by awemath on 7/9/15.
 */


import it.sevenbits.domain.Deal;
import it.sevenbits.service.DealService;
import it.sevenbits.service.UserService;
import it.sevenbits.service.exceptions.ServiceException;
import it.sevenbits.service.exceptions.UserServiceException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CommonFieldValidator {

    @Value("${date-time-format}")
    private String dateTimeFormat;

    /**
     * Email exists pattern
     */
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE
    );
    /**
     * Pattern for whitespaces
     */
    private static final String WHITESPACE_PATTERN = "\\s+";

    /**
     * Validate whether value is not null and empty or contains only spaces, otherwise reject it
     *
     * @param value  Value of field
     * @param errors Map for errors
     * @param field  Rejected field name
     * @param key    Rejected message key
     */
    public void isNotNullOrEmpty(
            final String value,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (!errors.containsKey(field)) {
            if (value == null) {
                errors.put(field, key);
            } else if (value.isEmpty()) {
                errors.put(field, key);
            } else if (value.matches(WHITESPACE_PATTERN)) {
                errors.put(field, key);
            }
        }
    }

    /**
     * Validate whether value is valid email, otherwise reject it
     *
     * @param value  Value of field
     * @param errors Map for errors
     * @param field  Rejected field name
     * @param key    Rejected message key
     */
    public void isEmail(final String value, final Map<String, String> errors, final String field, final String key) {
        if (value != null && !errors.containsKey(field)) {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(value);
            if (!matcher.find()) {
                errors.put(field, key);
            }
        }
    }

    /**
     * Validate, whether value is too long
     *
     * @param value     Value of field
     * @param maxLength Length allowed
     * @param errors    Map for errors
     * @param field     Rejected field name
     * @param key       Rejected message key
     */
    public void shorterThan(
            final String value,
            final Integer maxLength,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (errors.containsKey(field)) {
            return;
        }
        if (value != null) {
            if (value.length() > maxLength) {
                errors.put(field, key);
            }
        }
    }

    public void isNum(
            final String value,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        char c;
        if (errors.containsKey(field)) {
            return;
        }
        char[] buf = value.toCharArray();
        if (buf[0] == '-' || (buf[0] <= '9' && buf[0] >= '0')) {
            for (int i = 1; i < value.length(); i++) {
                c = buf[i];
                if ((c < '0' || c > '9')) {
                    errors.put(field, key);
                }
            }
        } else {
            errors.put(field, key);
        }
    }

    public void isPositiveNum(
            final String value,
            final Map<String, String> errors,
            final String field,
            final String key
    ) {
        if (errors.containsKey(field)) {
            return;
        }
        char[] buf = value.toCharArray();
        if (value.length() > 0) {
            if (buf[0] == '-' || Double.valueOf(value) <= 0) {
                errors.put(field, key);
                return;
            }
        }
    }

    public void equalPasswords(
            final String password,
            final String passwordVerification,
            final Map<String, String> errors,
            final String key,
            final String field
    ) {
        if (!errors.containsKey(key)) {
            if (!password.equals(passwordVerification)) {
                errors.put(key, field);
            }
        }
    }

    public void isUniqueEmail(
            final String email,
            final UserService userService,
            final Map<String, String> errors,
            final String key,
            final String field
    ) throws UserServiceException {
        if (!errors.containsKey(key)) {
            if (userService.getCountOfUsersWithEmail(email) != 0) {
                errors.put(key, field);
            }
        }
    }

    public void isNotEqualStrings(
            final String firstString,
            final String secondString,
            final Map<String, String> errors,
            final String key,
            final String field
    ) {
        if (!errors.containsKey(key)) {
            if (firstString.equals(secondString)) {
                errors.put(key, field);
            }
        }
    }


    public void isTooEarlyDate(
            final String from,
            final HashMap<String, String> errors,
            final String key,
            final String field) {
        if (errors.isEmpty()) {

            DateTimeFormatter format = DateTimeFormat.forPattern(dateTimeFormat);
            DateTime start = format.parseDateTime(from);
            if (start.getMillis() < DateTime.now().getMillis()) {
                errors.put(key, field);
            }
        }
    }

    public void isEndAfterStart(
            final String from,
            final String to,
            final HashMap<String, String> errors,
            final String key,
            final String field) {
        if (errors.isEmpty()) {
            DateTimeFormatter format = DateTimeFormat.forPattern(dateTimeFormat);
            DateTime start = format.parseDateTime(from);
            DateTime end = format.parseDateTime(to);
            if (start.getMillis() > end.getMillis()) {
                errors.put(key, field);
            }
        }
    }


    public void isEarlierThenWeek(
            final String from,
            final HashMap<String, String> errors,
            final String key,
            final String value) {
        if (errors.isEmpty()) {
            DateTimeFormatter format = DateTimeFormat.forPattern(dateTimeFormat);
            DateTime start = format.parseDateTime(from);
            if (DateTime.now().plusWeeks(1).getMillis() < start.getMillis()) {
                errors.put(key, value);
            }
        }
    }

    public void isRentTimeLessMonth(
            final String from,
            final String to,
            final HashMap<String, String> errors,
            final String key,
            final String field) {
        if (errors.isEmpty()) {
            DateTimeFormatter format = DateTimeFormat.forPattern(dateTimeFormat);
            DateTime start = format.parseDateTime(from);
            DateTime end = format.parseDateTime(to);
            if (start.plusMonths(1).getMillis() < end.getMillis()) {
                errors.put(key, field);
            }
        }
    }

    public void isRentTimeMoreHour(
            final String from,
            final String to,
            final HashMap<String, String> errors,
            final String key,
            final String field) {
        if (errors.isEmpty()) {
            DateTimeFormatter format = DateTimeFormat.forPattern(dateTimeFormat);
            DateTime start = format.parseDateTime(from);
            DateTime finish = format.parseDateTime(to);
            if (start.plusHours(1).getMillis() > finish.getMillis()) {
                errors.put(key, field);
            }
        }
    }

    public void isImage(
            final String imageUrl,
            final HashMap<String, String> errors,
            final String key,
            final String field) {
        if (errors.containsKey(key) || imageUrl == "") {
            return;
        }
        if (!(imageUrl.contains(".jpg") ||
                imageUrl.contains(".bmp") ||
                imageUrl.contains(".jpeg") ||
                imageUrl.contains(".png") ||
                imageUrl.contains(".gif"))) {
            errors.put(key, field);
        }
    }

    public void isGoodsAlreadyEngage(
            final String from,
            final String to,
            final long goodsId,
            final DealService service,
            final HashMap<String, String> errors,
            final String key,
            final String field) throws ServiceException {
        if (errors.isEmpty()) {
            DateTimeFormatter format = DateTimeFormat.forPattern(dateTimeFormat);
            DateTime start = format.parseDateTime(from);
            DateTime end = format.parseDateTime(to);
            List<Deal> openDeals = service.getOpenWithId(goodsId);
            Deal deal;
            for (int i = 0; i < openDeals.size(); i++) {
                deal = openDeals.get(i);
                DateTime estimatedStart = DateTime.parse(deal.getEstimateStartDate());
                DateTime estimatedEnd = DateTime.parse(deal.getEstimateEndDate());
                if (estimatedStart.getMillis() <= start.getMillis() && estimatedEnd.getMillis() >= start.getMillis()) {
                    errors.put(key, field);
                    return;
                }
                if (start.getMillis() <= estimatedStart.getMillis() && end.getMillis() >= estimatedStart.getMillis()) {
                    errors.put(key, field);
                    return;
                }

            }
        }
    }

    public void isImages(
            final List<String> imageUrl,
            final HashMap<String, String> errors,
            final String key,
            final String field) {
        for (String i : imageUrl) {
            if (i != null && !i.isEmpty()) {
                if (!i.endsWith(".jpeg") && !i.endsWith(".jpg") &&
                        !i.endsWith(".png") && !i.endsWith(".bmp")) {
                    errors.put("Изображения", "Допускаются только изображения в форматах png, bmp, jpg, jpeg");
                    return;
                }
            }
        }
    }
}

