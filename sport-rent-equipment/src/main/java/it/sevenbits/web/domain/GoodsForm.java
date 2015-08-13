package it.sevenbits.web.domain;

/**
 * Created by awemath on 7/8/15.
 */
public class GoodsForm {
    private long id;
    private String title;
    private String description;
    private String pledge = "no";
    private String pricePerHour, pricePerDay, pricePerWeek;
    private String author;
    private String authorPhone;
    private String firstImage, secondImage, thirdImage;


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
        form.setPricePerHour(String.valueOf((int)goods.getPricePerHour()));
        form.setPricePerDay(String.valueOf((int)goods.getPricePerDay()));
        form.setPricePerWeek(String.valueOf((int)goods.getPricePerWeek()));
        return form;
    }

    public String getFirstImage() {
        return firstImage;
    }

    public void setFirstImage(String firstImage) {
        this.firstImage = firstImage;
    }

    public String getSecondImage() {
        return secondImage;
    }

    public void setSecondImage(String secondImage) {
        this.secondImage = secondImage;
    }

    public String getThirdImage() {
        return thirdImage;
    }

    public void setThirdImage(String thirdImage) {
        this.thirdImage = thirdImage;
    }
}
