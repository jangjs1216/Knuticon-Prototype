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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    String name="";

    //Firebase Reference
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    //GridView Adapter
    MyGridViewAdapter adapter;
    ArrayList<String> items = new ArrayList<>();;

    //Metadata
    String level1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        // 태그 값 가져오기
        Intent it = getIntent();
        String category = it.getStringExtra("it_category");
        level1 = it.getStringExtra("level1");

        // 텍스트뷰에 카테고리 적용
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_category);
        TextView tv_categoryname = (TextView)findViewById(R.id.tv_categoryname);
        tv_categoryname.setText(category);

        // GridView에 정보 삽입
        getInfo();
    }
    public void getInfo(){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.ll_category);
        TextView brand= new TextView(this);
        linearLayout.addView(brand);

        reference.child("category").child(level1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GridView gv = (GridView)findViewById(R.id.gv);

                items.clear();
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

                        Intent it = new Intent(getApplicationContext(), ItemActivity.class);
                        it.putExtra("it_item", item_name);
                        it.putExtra("level1", level1);
                        it.putExtra("level2", item_name);
                        startActivity(it);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        brand.setText(name);
    }

//    public void goods_menu(View v){
//        // 카테고리 선택
//        int id = v.getId();
//        TextView tv = (TextView)findViewById(id);
//        String category = (String)tv.getText();
//        String tag = (String)tv.getTag();
//
//        Toast.makeText(CategoryActivity.this, category + "를 선택하였습니다.", Toast.LENGTH_SHORT).show();
//
//        // 태그 값 씌워서 인텐트로 보내기
//    }
}