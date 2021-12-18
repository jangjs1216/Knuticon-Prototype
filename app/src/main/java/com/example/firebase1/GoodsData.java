package com.example.firebase1;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class GoodsData {
    public int discount;
    public int price;
    //유통기한
    public String itemname;
    public String date;
    public String email;
    public String owner;
    public String gifticon_uri;
    public String desc;

    public GoodsData() {
        discount = 0;
        price = 0;
        itemname = new String();
        date = new String();
        email = new String();
        owner = new String();
        gifticon_uri = new String();
        desc = new String();
    }

    public GoodsData(int discount, int price, String itemname, String date, String email, String owner, String gifticon_uri, String desc){
        this.discount = discount;
        this.price = price;
        this.itemname = itemname;
        this.date = date;
        this.email = email;
        this.owner = owner;
        this.gifticon_uri = gifticon_uri;
        this.desc = desc;
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

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void InsertGoodsToStorage(String newOwner)
    {
        DatabaseReference addDatabase = FirebaseDatabase.getInstance().getReference("storage");

        this.owner = newOwner;

        String key = addDatabase.push().getKey();
        addDatabase.child(gifticon_uri).setValue(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.e("###", "상품을 보관함에 저장하였습니다.");
                }
            }
        });
    }
}
