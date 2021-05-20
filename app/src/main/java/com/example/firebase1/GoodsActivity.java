package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GoodsActivity extends AppCompatActivity {

    String name="";

    //Firebase Reference
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    private FirebaseAuth mFirebaseAuth;

    //데이터베이스 추가용
    private DatabaseReference addDatabase;
    private String userUrl = "";

    //metadata
    String level1, level2, level3;

    //GridView Adapter
    MyGridViewAdapter3 adapter;
    ArrayList<GoodsData> items = new ArrayList<>();

    //판매 올리기 구현
    EditText et_discount;
    Button btn_sell;

    //가격
    String price="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        // 에딧텍스트 가져오기
        et_discount = (EditText)findViewById(R.id.et_discount);
        btn_sell = (Button)findViewById(R.id.btn_sell);

        // 태그 값 가져오기
        Intent it = getIntent();
        String category = it.getStringExtra("it_goods");
        level1 = it.getStringExtra("level1");
        level2 = it.getStringExtra("level2");
        level3 = it.getStringExtra("level3");

        // 텍스트뷰에 카테고리 적용
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_category);
        TextView tv_goods = (TextView)findViewById(R.id.tv_goods);
        tv_goods.setText(category);

        // GridView에 정보 삽입
        getInfo();
        getPrice();
    }

    public void getInfo(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_category);
        TextView brand= new TextView(this);
        linearLayout.addView(brand);

        reference.child("category").child(level1).child(level2).child(level3).child("goods").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GridView gv = (GridView)findViewById(R.id.gv_goods);

                items.clear();
                for (final DataSnapshot data : snapshot.getChildren()) {
                    GoodsData gds = data.getValue(GoodsData.class);
                    items.add(gds);
                }

                adapter = new MyGridViewAdapter3(items, getApplicationContext());
                //데이터 업데이트 ( 중복 표시 방지 )
                adapter.notifyDataSetChanged();

                gv.setAdapter(adapter);

                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item_name = (String) adapter.getItem(position).toString();
                        Toast.makeText(getApplicationContext(), item_name+"이 아이템이 선택되었습니다", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        brand.setText(name);
    }

    public void getPrice(){
        Log.d("####", "This place");
        //데이터베이스에서 정가 값 가져오기

        addDatabase = FirebaseDatabase.getInstance().getReference("category").child(level1).child(level2).child(level3);
        addDatabase.child("price").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("####", snapshot1.getValue().toString());
                    price = snapshot1.getValue().toString();
                    Log.d("####", price);
                    Toast.makeText(getApplicationContext(), price, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sell_goods(View v){
        final FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
        userUrl = user.getUid();

        //날짜 가져오기
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

        //데이터베이스 연동
        addDatabase = FirebaseDatabase.getInstance().getReference("category").child(level1).child(level2).child(level3);

        //텍스트뷰 값 가져오기
        String name = et_discount.getText().toString();

        Toast.makeText(getApplicationContext(), price, Toast.LENGTH_LONG).show();

        //GoodsData 객체 생성
        GoodsData goodsData = new GoodsData(
                Integer.parseInt(name),
                Integer.parseInt(price),
                dateFormat.format(new Date()),
                user.getEmail()
        );

        String key = addDatabase.push().getKey();
        addDatabase.child("goods").child(key).setValue(goodsData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //삽입이 완료되면 할 행동
                }
            }
        });
//        addDatabase.child(name).child("price").setValue(name);
//        addDatabase.child(name).child("seller").setValue(firebaseUser.getUid());
    }
}