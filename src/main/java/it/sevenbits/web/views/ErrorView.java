package it.sevenbits.web.views;

/**
 * Created by awemath on 10/8/15.
 */
public class ErrorView {
    private String key, value;

    ErrorView(
            final String key,
            final String value) {
        setKey(key);
        setValue(value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
