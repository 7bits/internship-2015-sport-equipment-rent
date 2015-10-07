package it.sevenbits.domain;

/**
 * Created by awemath on 7/21/15.
 */
public class Deal {
    private long id;
    private long landlordId;
    private long rentingId;
    private long goodsId;
    private boolean isClosed;
    private boolean isAnswered;
    private boolean isHanded;           //gave
    private boolean acceptedRenting;    //take
    private boolean acceptedReturn;     //return
    private String estimateStartDate;
    private String estimateEndDate;
    private String realStartDate;
    private String realEndDate;


    public Deal(){
    }

    public Deal(
            final long landlordId,
            final long rentingId,
            final long goodsId) {
        this.landlordId = landlordId;
        this.rentingId = rentingId;
        this.goodsId = goodsId;
        this.isHanded = false;
        this.isAnswered = false;
        this.isClosed = false;

    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(final boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public long getLandlordId() {
        return landlordId;
    }

    public void setLandlordId(final long landlordId) {
        this.landlordId = landlordId;
    }

    public long getRentingId() {
        return rentingId;
    }

    public void setRentingId(final long rentingId) {
        this.rentingId = rentingId;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(final long goodsId) {
        this.goodsId = goodsId;
    }

    public boolean isHanded() {
        return isHanded;
    }

    public void setIsAccepted(final boolean isAccepted) {
        this.isHanded = isAccepted;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setIsClosed(final boolean isClosed) {
        this.isClosed = isClosed;
    }

    public void setIsHanded(final boolean isHanded) {
        this.isHanded = isHanded;
    }

    public boolean isAcceptedRenting() {
        return acceptedRenting;
    }

    public void setAcceptedRenting(final boolean acceptedRenting) {
        this.acceptedRenting = acceptedRenting;
    }

    public boolean isAcceptedReturn() {
        return acceptedReturn;
    }

    public void setAcceptedReturn(final boolean acceptedReturn) {
        this.acceptedReturn = acceptedReturn;
    }

    public String getEstimateStartDate() {
        return estimateStartDate;
    }

    public void setEstimateStartDate(final String estimateStartDate) {
        this.estimateStartDate = estimateStartDate;
    }

    public String getEstimateEndDate() {
        return estimateEndDate;
    }

    public void setEstimateEndDate(final String estimateEndDate) {
        this.estimateEndDate = estimateEndDate;
    }

    public String getRealStartDate() {
        return realStartDate;
    }

    public void setRealStartDate(final String realStartDate) {
        this.realStartDate = realStartDate;
    }

    public String getRealEndDate() {
        return realEndDate;
    }

    public void setRealEndDate(final String realEndDate) {
        this.realEndDate = realEndDate;
    }

}
