package it.sevenbits.web.domain;

import java.io.Serializable;

/**
 * Created by awemath on 7/8/15.
 */
public class Goods implements Serializable {
    Long id;
    String email;
    String title;
    String description;
    String pledge;
    String pricePerHour, pricePerDay, pricePerWeek;
    boolean status;
    String author;
    String authorPhone;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPledge() {
        return pledge;
    }

    public void setPledge(String pledge) {
        this.pledge = pledge;
    }

    public Double getPricePerHour() {
        return Double.valueOf(pricePerHour);
    }

    public void setPricePerHour(String pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public Double getPricePerDay() {
        return Double.valueOf(pricePerDay);
    }

    public void setPricePerDay(String pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Double getPricePerWeek() {
        return Double.valueOf(pricePerWeek);
    }

    public void setPricePerWeek(String pricePerWeek) {
        this.pricePerWeek = pricePerWeek;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorPhone() {
        return authorPhone;
    }

    public void setAuthorPhone(String authorPhone) {
        this.authorPhone = authorPhone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString(){
        return String.format("Goods[id=%d, title=%s, author=%s]",
                + id, title, author);
    }

}
