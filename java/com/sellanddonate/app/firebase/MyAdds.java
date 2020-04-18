package com.sellanddonate.app.firebase;

import com.google.firebase.database.PropertyName;

import java.util.ArrayList;

public class MyAdds {

    @PropertyName("description")
    private String description;

    @PropertyName("download_url")
    private ArrayList<String> download_url;

    @PropertyName("price")
    private String price;

    @PropertyName("title")
    private String title;

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
}
