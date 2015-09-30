package it.sevenbits.web.views;

import it.sevenbits.domain.HistoryRow;

/**
 * Created by awemath on 8/19/15.
 */
public class HistoryRowView {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public static HistoryRowView valueOf(HistoryRow historyRow) {
        HistoryRowView historyRowView = new HistoryRowView();
        historyRowView.setTitle(historyRow.getTitle());
        historyRowView.setRentingId(historyRow.getRentingId());
        historyRowView.setRenting(historyRow.getRenting());
        historyRowView.setStartDate(historyRow.getStartDate());
        historyRowView.setEndDate(historyRow.getEndDate());
        historyRowView.setRentingImage(historyRow.getRentingImage());
        return historyRowView;
    }
}
