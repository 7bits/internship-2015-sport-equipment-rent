package it.sevenbits.web.forms;

import it.sevenbits.domain.User;

/**
 * Created by awemath on 8/7/15.
 */
public class UpdateUserForm {
    String firstName;
    String phone;
    String imageUrl;
    String pass;
    String newPass;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
    public static UpdateUserForm valueOf(User user){
        UpdateUserForm form = new UpdateUserForm();
        form.firstName = user.getFirstName();
        form.phone = user.getPhone();
        form.imageUrl = user.getImageUrl();
        return form;
    }
}
