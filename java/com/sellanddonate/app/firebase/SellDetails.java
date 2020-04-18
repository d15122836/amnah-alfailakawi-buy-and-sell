package com.sellanddonate.app.firebase;

import com.google.firebase.database.PropertyName;

import java.util.ArrayList;

public class SellDetails  {
    private String productId;
    int bid;
    double lat,longt;
    private boolean available;

    @PropertyName("description")
    private String description;

    @PropertyName("download_url")
    private ArrayList<String> download_url;
    @PropertyName("price")
    private String price;

    @PropertyName("title")
    private String title;


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

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getDownload_url() {
        return download_url;
    }

    public void setDownload_url(ArrayList<String> download_url) {
        this.download_url = download_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
