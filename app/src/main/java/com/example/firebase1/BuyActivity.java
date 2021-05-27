package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class BuyActivity extends AppCompatActivity {

    //데이터베이스 연동
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    private FirebaseAuth mFirebaseAuth;

    //TextView 가져오기
    TextView tv_buy_info;
    TextView tv_buy_price;
    TextView tv_cur_point;
    TextView tv_next_point;

    String level1, level2, level3, price, GoodsInfo;

    String curPoint, nextPoint;

    //판매자 정보
    String sellerPoint;

    //상품정보

    String goods_date;
    int goods_discount;
    String goods_email;
    String goods_gifticon_uri;
    String goods_owner;
    String goods_itemname;
    int goods_price;

    //구매자와 판매자
    UserInfo sellerInfo;
    UserInfo buyerInfo;

    //구매자 Key
    String buyerKey;

    Boolean isPerchase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        isPerchase = false;

        // 현재 로그인된 사용자 정보

        final FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
        buyerKey = user.getUid();

        // 텍스트뷰 아이디 가져오기
        tv_buy_info = (TextView) findViewById(R.id.tv_buy_info);
        tv_buy_price = (TextView) findViewById(R.id.tv_buy_price);
        tv_cur_point = (TextView) findViewById(R.id.tv_cur_point);
        tv_next_point = (TextView) findViewById(R.id.tv_next_point);

        // 태그 값 가져오기
        Intent it = getIntent();
        String category = it.getStringExtra("it_goods");
        level1 = it.getStringExtra("level1");
        level2 = it.getStringExtra("level2");
        level3 = it.getStringExtra("level3");
        price = it.getStringExtra("price");
        GoodsInfo = it.getStringExtra("GoodsInfo");

        //상품 정보 가져오는 구문

        //B. 데이터베이스에서 값 가져오는 구문
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if (firebaseUser == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else {
            DocumentReference docRef = db.collection("users").document(firebaseUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            curPoint = document.get("point").toString();
                            tv_cur_point.setText(curPoint);
                            nextPoint = String.valueOf(Integer.parseInt(curPoint) - Integer.parseInt(price));
                            tv_next_point.setText(nextPoint);
                        }
                    }
                }
            });
        }
        ;

        //nextPoint = String.valueOf(Integer.parseInt(curPoint) - Integer.parseInt(price));

        //상품 정보 가져오기
        reference.child("category").child(level1).child(level2).child(level3).child("goods").child(GoodsInfo).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("###", "에러발생 ", task.getException());
                } else {
                    GoodsData goodsData = task.getResult().getValue(GoodsData.class);
                    goods_date = goodsData.date;
                    goods_discount = goodsData.discount;
                    goods_email = goodsData.email;
                    goods_gifticon_uri = goodsData.gifticon_uri;
                    goods_owner = goodsData.owner;
                    goods_price = goodsData.price;
                    goods_itemname = goodsData.itemname;

                    tv_buy_info.setText(goods_itemname);
                    tv_buy_price.setText(String.valueOf(goods_discount));

                    Log.e("###", "성공!" + goods_owner);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isPerchase)
        {
            Log.e("###", "해당 인텐트 종료! 업데이트할 것 : " + (sellerInfo.getPoint() + goods_price));
            sellerInfo.setUserPoint(goods_owner, sellerInfo.getPoint() + goods_price);
            buyerInfo.setUserPoint(buyerKey, buyerInfo.getPoint() - goods_price);

            reference.child("category").child(level1).child(level2).child(level3).child("goods").child(GoodsInfo).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("###", "에러발생 ", task.getException());
                    } else {
                        GoodsData goodsData = task.getResult().getValue(GoodsData.class);
                        goods_date = goodsData.date;
                        goods_discount = goodsData.discount;
                        goods_email = goodsData.email;
                        goods_gifticon_uri = goodsData.gifticon_uri;
                        goods_owner = goodsData.owner;
                        goods_price = goodsData.price;
                        goods_itemname = goodsData.itemname;

                        //보관함에 상품 저장하기
                        goodsData.InsertGoodsToStorage(buyerKey);

                        tv_buy_info.setText(goods_itemname);
                        tv_buy_price.setText(String.valueOf(goods_discount));

                        Log.e("###", "성공!" + goods_owner);
                    }
                }
            });

            reference.child("category").child(level1).child(level2).child(level3).child("goods").child(GoodsInfo).removeValue();
        }
    }

    public void buy_goods(View v) {
        Log.e("###", "현재 상품 정보 : " + GoodsInfo);
        isPerchase = true;

        sellerInfo = new UserInfo(goods_owner);
        buyerInfo = new UserInfo(buyerKey);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        finish();
    }
}
