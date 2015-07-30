package it.sevenbits.web.domain;

import java.sql.Date;

/**
 * Created by awemath on 7/30/15.
 */
public class DateForm {
    Date from;
    Date to;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
