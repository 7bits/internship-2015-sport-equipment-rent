package it.sevenbits.web.views;

import java.util.Map;

/**
 * Created by awemath on 10/6/15.
 */
public class GetAnnouncementView {
    private boolean isSuccess;
    private Map<String, String> errors;
    private String from, to;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(final boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(final Map<String, String> errors) {
        this.errors = errors;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(final String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(final String to) {
        this.to = to;
    }
}
