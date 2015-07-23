package it.sevenbits.web.domain;

/**
 * Created by awemath on 7/8/15.
 */
public class GoodsForm {
    private long id;
    private String title;
    private String description;
    private String pledge;
    private String pricePerHour, pricePerDay, pricePerWeek;
    private String author;
    private String authorPhone;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(String pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public String getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(String pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getPricePerWeek() {
        return pricePerWeek;
    }

    public void setPricePerWeek(String pricePerWeek) {
        this.pricePerWeek = pricePerWeek;
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

    public static GoodsForm valueOf(Goods goods) {
        GoodsForm form = new GoodsForm();
        form.setId(goods.getId());
        form.setTitle(goods.getTitle());
        form.setAuthor(goods.getAuthor());
        form.setPledge(goods.getPledge());
        form.setDescription(goods.getDescription());
        form.setPricePerHour(String.valueOf(goods.getPricePerHour()));
        form.setPricePerDay(String.valueOf(goods.getPricePerDay()));
        form.setPricePerWeek(String.valueOf(goods.getPricePerWeek()));
        return form;
    }
}
