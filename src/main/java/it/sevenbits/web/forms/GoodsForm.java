package it.sevenbits.web.forms;

import it.sevenbits.domain.Goods;
import it.sevenbits.domain.User;

import java.util.LinkedList;
import java.util.List;

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
    private List<String> imageUrl = new LinkedList<>();


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
    public List<String> getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List<String> imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void addImageUrl(String imageUrl){
        this.imageUrl.add(imageUrl);
    }

    public Goods toGoods(User user){
        GoodsForm form = this;
        Goods goods = new Goods();
        goods.setTitle(form.getTitle());
        goods.setAuthorId(user.getId());
        goods.setAuthorPhone(user.getPhone());
        goods.setAuthor(user.getFirstName());
        goods.setDescription(form.getDescription());
        goods.setPledge(form.getPledge());
        goods.setPricePerHour(Double.valueOf(form.getPricePerHour()));
        goods.setPricePerDay(Double.valueOf(form.getPricePerDay()));
        goods.setPricePerWeek(Double.valueOf(form.getPricePerWeek()));
        return goods;
    }
    public Goods toGoods(){
        GoodsForm form = this;
        Goods goods = new Goods();
        goods.setTitle(form.getTitle());
        goods.setDescription(form.getDescription());
        goods.setPledge(form.getPledge());
        goods.setPricePerHour(Double.valueOf(form.getPricePerHour()));
        goods.setPricePerDay(Double.valueOf(form.getPricePerDay()));
        goods.setPricePerWeek(Double.valueOf(form.getPricePerWeek()));
        return goods;
    }
}
