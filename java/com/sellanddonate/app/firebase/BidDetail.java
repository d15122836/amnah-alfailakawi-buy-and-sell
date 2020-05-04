package com.sellanddonate.app.firebase;

import com.google.firebase.database.PropertyName;

import java.util.ArrayList;

public class BidDetail {

    private String payment;
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }



    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @PropertyName("description")
    private String bidAmount;

    double lat,longt;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLongt() {
        return longt;
    }

    public void setLongt(double longt) {
        this.longt = longt;
    }

    @PropertyName("price")
    private String username;

    private String status;

    public String getbidAmount() {
        return bidAmount;
    }

    public void setbidAmount(String bidAmount) {
        this.bidAmount = bidAmount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @PropertyName("title")
    private String title;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
