package it.sevenbits.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Created by awemath on 7/8/15.
 */
public class Goods implements Serializable {

    Long id;
    String email;
    String title;
    String description;
    String pledge;
    int pricePerHour, pricePerDay, pricePerWeek;
    boolean status;
    Long authorId;
    String author;//name
    String authorPhone;
    boolean visible;
    List<String> imageUrl;
    String authorImage;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

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

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = (int)pricePerHour;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = (int)pricePerDay;
    }

    public int getPricePerWeek() {
        return pricePerWeek;
    }

    public void setPricePerWeek(double pricePerWeek) {
        this.pricePerWeek = (int)pricePerWeek;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    @Override
    public String toString(){
        return String.format("Goods[id=%d, title=%s, author=%s]",
                + id, title, authorId);
    }

}
