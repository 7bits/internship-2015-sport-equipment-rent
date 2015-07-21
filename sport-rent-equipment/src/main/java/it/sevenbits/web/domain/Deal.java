package it.sevenbits.web.domain;

/**
 * Created by awemath on 7/21/15.
 */
public class Deal {
    long id;
    User landlord;
    User renting;
    Goods goods;
    boolean isAccepted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getLandlord() {
        return landlord;
    }

    public void setLandlord(User landlord) {
        this.landlord = landlord;
    }

    public long landlordId(){
        return landlord.getId();
    }

    public User getRenting() {
        return renting;
    }

    public void setRenting(User renting) {
        this.renting = renting;
    }
    public long getRentingId(){
        return renting.getId();
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
}
