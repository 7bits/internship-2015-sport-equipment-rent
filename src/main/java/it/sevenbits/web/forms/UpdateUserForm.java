package it.sevenbits.web.forms;

import it.sevenbits.domain.User;

/**
 * Created by awemath on 8/7/15.
 */
public class UpdateUserForm {
    private String firstName;
    private String phone;
    private String imageUrl;
    private String pass;
    private String newPass;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(final String pass) {
        this.pass = pass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(final String newPass) {
        this.newPass = newPass;
    }

    public static UpdateUserForm valueOf(final User user) {
        UpdateUserForm form = new UpdateUserForm();
        form.firstName = user.getFirstName();
        form.phone = user.getPhone();
        form.imageUrl = user.getImageUrl();
        return form;
    }
}
