package it.sevenbits.web.domain;

/**
 * Created by awemath on 7/21/15.
 */
public class Deal {
    long id;
    long landlordId;
    long rentingId;
    long goodsId;
    boolean isAccepted;
    boolean isAnswered;

    public Deal(){
    }

    public Deal(long landlordId, long rentingId, long goodsId){
        this.landlordId = landlordId;
        this.rentingId = rentingId;
        this.goodsId = goodsId;
        this.isAccepted = false;
        this.isAnswered = false;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(long landlordId) {
        this.landlordId = landlordId;
    }

    public long getRentingId() {
        return rentingId;
    }

    public void setRentingId(long rentingId) {
        this.rentingId = rentingId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isAccepted = isAccepted;
    }
}
