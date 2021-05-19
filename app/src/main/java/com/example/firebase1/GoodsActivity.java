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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

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
    MyGridViewAdapter adapter;
    ArrayList<String> items = new ArrayList<>();

    //판매 올리기 구현
    EditText et_discount;
    Button btn_sell;

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
    }
    public void getInfo(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_category);
        TextView brand= new TextView(this);
        linearLayout.addView(brand);

        reference.child("category").child(level1).child(level2).child(level3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GridView gv = (GridView)findViewById(R.id.gv_goods);

                for (final DataSnapshot data : snapshot.getChildren()) {
                    String cafeName = data.getKey();
                    items.add(new String(cafeName));
                }

                adapter = new MyGridViewAdapter(items, getApplicationContext());
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

    public void sell_goods(View v){
        final FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
        userUrl = user.getUid();

        addDatabase = FirebaseDatabase.getInstance().getReference("category").child("cafe").child("starbucks").child("icedamericano");

        String name = et_discount.getText().toString();
        addDatabase.push().child(name);
        addDatabase.child(name).child("name").setValue(name);
    }
}