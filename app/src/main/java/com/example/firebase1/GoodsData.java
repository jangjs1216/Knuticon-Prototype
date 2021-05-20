package com.example.firebase1;

public class GoodsData {
    public int discount;
    public int price;
    //유통기한
    public String date;
    public String email;

    public GoodsData() {
        discount = 0;
        price = 0;
        date = new String();
        email = new String();
    }

    public GoodsData(int discount, int price, String date, String email){
        this.discount = discount;
        this.price = price;
        this.date = date;
        this.email = email;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
