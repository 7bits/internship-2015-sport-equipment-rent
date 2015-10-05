package it.sevenbits.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by awemath on 7/8/15.
 */
public class Goods implements Serializable {

    private Long id;
    private String email;
    private String title;
    private String description;
    private String pledge;
    private int pricePerHour, pricePerDay, pricePerWeek;
    private boolean status;
    private Long authorId;
    private String author; //name
    private String authorPhone;
    private boolean visible;
    private List<String> imageUrl = new LinkedList<String>();
    private String authorImage;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getPledge() {
        return pledge;
    }

    public void setPledge(final String pledge) {
        this.pledge = pledge;
    }

    public int getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(final double pricePerHour) {
        this.pricePerHour = (int) pricePerHour;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(final double pricePerDay) {
        this.pricePerDay = (int) pricePerDay;
    }

    public int getPricePerWeek() {
        return pricePerWeek;
    }

    public void setPricePerWeek(final double pricePerWeek) {
        this.pricePerWeek = (int) pricePerWeek;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(final boolean status) {
        this.status = status;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(final Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public String getAuthorPhone() {
        return authorPhone;
    }

    public void setAuthorPhone(final String authorPhone) {
        this.authorPhone = authorPhone;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void addImageUrl(final String imageUrl) {
        this.imageUrl.add(imageUrl);
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(final String authorImage) {
        this.authorImage = authorImage;
    }

    @Override
    public String toString() {
        return String.format("Goods[id=%d, title=%s, author=%s]",
                id, title, authorId);
    }

}
