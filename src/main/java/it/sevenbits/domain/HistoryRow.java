package it.sevenbits.domain;

/**
 * Created by awemath on 9/30/15.
 */
public class HistoryRow {
    String startDate, endDate, renting, rentingId, rentingImage, title;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getRenting() {
        return renting;
    }

    public void setRenting(String renting) {
        this.renting = renting;
    }

    public String getRentingId() {
        return rentingId;
    }

    public void setRentingId(String rentingId) {
        this.rentingId = rentingId;
    }

    public String getRentingImage() {
        return rentingImage;
    }

    public void setRentingImage(String rentingImage) {
        this.rentingImage = rentingImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
