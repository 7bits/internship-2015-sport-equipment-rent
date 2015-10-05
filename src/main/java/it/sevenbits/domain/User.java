package it.sevenbits.domain;

import it.sevenbits.web.forms.UpdateUserForm;

import java.io.Serializable;

/**
 * Created by awemath on 7/14/15.
 */
public class User implements Serializable {
    private Long id;
    private String firstName;
    private String secondName;
    private String phone;
    private String email;
    private String pass;
    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(final String pass) {
        this.pass = pass;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static User valueOf(final UpdateUserForm form) {
        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setPhone(form.getPhone());
        user.setImageUrl(form.getImageUrl());
        return user;
    }
}
