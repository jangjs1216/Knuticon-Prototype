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
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    private FirebaseAuth mFirebaseAuth;

    //TextView 가져오기
    TextView tv_buy_info;
    TextView tv_buy_price;
    TextView tv_cur_point;
    TextView tv_next_point;

    String level1, level2, level3, price, GoodsInfo;

    String curPoint, nextPoint;

    //상품정보

    String goods_date;
    int goods_discount;
    String goods_email;
    String goods_gifticon_uri;
    String goods_owner;
    int goods_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        // 텍스트뷰 아이디 가져오기
        tv_buy_info = (TextView)findViewById(R.id.tv_buy_info);
        tv_buy_price = (TextView)findViewById(R.id.tv_buy_price);
        tv_cur_point = (TextView)findViewById(R.id.tv_cur_point);
        tv_next_point = (TextView)findViewById(R.id.tv_next_point);

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
        if(firebaseUser == null){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }else {
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
        };

        //nextPoint = String.valueOf(Integer.parseInt(curPoint) - Integer.parseInt(price));

        tv_buy_info.setText(GoodsInfo);
        tv_buy_price.setText(price);
    }

    public void buy_goods(View v) {
        reference.child("category").child(level1).child(level2).child(level3).child("goods").child(GoodsInfo).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                goods_date = (String) snapshot.child("date").getValue();
                goods_discount = (int) snapshot.child("discount").getValue();
                goods_email = (String) snapshot.child("email").getValue();
                goods_gifticon_uri = (String) snapshot.child("gifticon_uri").getValue();
                goods_owner = (String) snapshot.child("owner").getValue();
                goods_price = (int) snapshot.child("price").getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}