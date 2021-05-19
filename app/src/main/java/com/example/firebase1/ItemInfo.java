package com.example.firebase1;

public class ItemInfo {
    String Item_name;
    int discount, price;

    ItemInfo(String item_name, int discount, int price){
        this.Item_name = item_name;
        this.discount = discount;
        this.price = price;
    }
}
