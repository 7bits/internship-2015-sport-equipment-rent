package it.sevenbits.web.forms;

/**
 * Created by awemath on 7/14/15.
 */
public class RegistrationForm {
    private String firstName;
    private String secondName;
    private String eMail;
    private String password;
    private String passwordVerification;
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(final String secondName) {
        this.secondName = secondName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(final String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getPasswordVerification() {
        return passwordVerification;
    }

    public void setPasswordVerification(final String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }
}
