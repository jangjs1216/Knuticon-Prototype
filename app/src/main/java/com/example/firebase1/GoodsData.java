package com.example.firebase1;

public class GoodsData {
    public int discount;
    public int price;
    //유통기한
    public String date;
    public String email;
    public String owner;
    public String gifticon_uri;

    public GoodsData() {
        discount = 0;
        price = 0;
        date = new String();
        email = new String();
        owner = new String();
        gifticon_uri = new String();
    }

    public GoodsData(int discount, int price, String date, String email, String owner, String gifticon_uri){
        this.discount = discount;
        this.price = price;
        this.date = date;
        this.email = email;
        this.owner = owner;
        this.gifticon_uri = gifticon_uri;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGifticon_uri() {
        return gifticon_uri;
    }

    public void setGifticon_uri(String gifticon_uri) {
        this.gifticon_uri = gifticon_uri;
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
