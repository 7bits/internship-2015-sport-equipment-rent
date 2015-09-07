package it.sevenbits.web.domain;

import java.io.Serializable;

/**
 * Created by awemath on 7/14/15.
 */
public class User implements Serializable {
    Long id;
    String firstName;
    String secondName;
    String phone;
    String email;
    String pass;
    String imageUrl;
    public Long getId() {
        return id;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static User valueOf(UpdateUserForm form) {
        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setPhone(form.getPhone());
        user.setImageUrl(form.getImageUrl());
        return user;
    }
}
