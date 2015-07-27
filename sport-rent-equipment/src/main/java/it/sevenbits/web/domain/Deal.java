package it.sevenbits.web.domain;

import java.sql.Date;

/**
 * Created by awemath on 7/21/15.
 */
public class Deal {
    long id;
    long landlordId;
    long rentingId;
    long goodsId;
    boolean isClosed;
    boolean isAnswered;
    boolean isHanded;           //gave
    boolean acceptedRenting;    //take
    boolean acceptedReturn;     //return
    Date estimateStartDate;
    Date estimateEndDate;
    Date realStartDate;
    Date realEndDate;


    public Deal(){
    }

    public Deal(long landlordId, long rentingId, long goodsId){
        this.landlordId = landlordId;
        this.rentingId = rentingId;
        this.goodsId = goodsId;
        this.isHanded = false;
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

    public boolean isHanded() {
        return isHanded;
    }

    public void setIsAccepted(boolean isAccepted) {
        this.isHanded = isAccepted;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setIsClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }

    public void setIsHanded(boolean isHanded) {
        this.isHanded = isHanded;
    }

    public boolean isAcceptedRenting() {
        return acceptedRenting;
    }

    public void setAcceptedRenting(boolean acceptedRenting) {
        this.acceptedRenting = acceptedRenting;
    }

    public boolean isAcceptedReturn() {
        return acceptedReturn;
    }

    public void setAcceptedReturn(boolean acceptedReturn) {
        this.acceptedReturn = acceptedReturn;
    }

    public Date getEstimateStartDate() {
        return estimateStartDate;
    }

    public void setEstimateStartDate(Date estimateStartDate) {
        this.estimateStartDate = estimateStartDate;
    }

    public Date getEstimateEndDate() {
        return estimateEndDate;
    }

    public void setEstimateEndDate(Date estimateEndDate) {
        this.estimateEndDate = estimateEndDate;
    }

    public Date getRealStartDate() {
        return realStartDate;
    }

    public void setRealStartDate(Date realStartDate) {
        this.realStartDate = realStartDate;
    }

    public Date getRealEndDate() {
        return realEndDate;
    }

    public void setRealEndDate(Date realEndDate) {
        this.realEndDate = realEndDate;
    }
}
