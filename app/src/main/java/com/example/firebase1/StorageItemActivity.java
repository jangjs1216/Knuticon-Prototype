package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class StorageItemActivity extends AppCompatActivity {
    //데이터베이스 연동
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference addDatabase;

    String gifticon_uri;
    String date;

    Button btn_over;
    Button btn_sendMsg;

    EditText et_myMsg;

    //GridView Adapter
    GridView gv;
    ChattingAdapter adapter;
    ArrayList<ChattingData> items = new ArrayList<>();

    String uid;

    Boolean Agree1, Agree2;

    final FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_item);

        uid = user.getUid();

        Agree1 = Agree2 = false;

        Intent it = getIntent();
        gifticon_uri = it.getStringExtra("gifticon_uri");
        date = it.getStringExtra("date");

        et_myMsg = findViewById(R.id.et_myMsg);
        btn_sendMsg = findViewById(R.id.btn_sendMsg);

        gv = findViewById(R.id.gv_chatting);

        btn_sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long millis = new Date().getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM월 dd일 hh:mm");
                String millisInString = dateFormat.format(new Date());

                ChattingData chattingData = new ChattingData(
                        et_myMsg.getText().toString(),
                        millisInString,
                        user.getUid()
                );

                reference.child("storage").child(gifticon_uri).child("chatting").push().setValue(chattingData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //삽입이 완료되면 할 행동
                        }
                    }
                });

                et_myMsg.setText("");
            }
        });

        btn_over = (Button)findViewById(R.id.btn_over);
        btn_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChattingData chattingData = new ChattingData(
                        "Agree",
                        "millisInString",
                        user.getUid()
                );
                reference.child("storage").child(gifticon_uri).child("chatting").push().setValue(chattingData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //삽입이 완료되면 할 행동
                        }
                    }
                });

                Toast.makeText(getApplicationContext(), "상대의 확인을 기다리는 중입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //GridView에 정보 삽입
        getInfo();
    }

    public void getInfo(){
        reference.child("storage").child(gifticon_uri).child("chatting").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                for (final DataSnapshot data : snapshot.getChildren()) {
                    ChattingData gds = data.getValue(ChattingData.class);
                    if(!gds.msg.equals("Agree"))
                    {
                        items.add(gds);
                    }else{
                        if(gds.uid.equals(user.getUid()))
                        {
                            Agree1 = true;
                        }else{
                            Agree2 = true;
                        }
                    }
                }

                if(Agree1 && Agree2)
                {
                    reference.child("storage").child(gifticon_uri).removeValue();

                    Toast.makeText(getApplicationContext(), "상품 거래가 완료되었습니다!!", Toast.LENGTH_SHORT).show();
                    finish();
                }

                adapter = new ChattingAdapter(items, uid, getApplicationContext());
                //데이터 업데이트 ( 중복 표시 방지 )
                adapter.notifyDataSetChanged();

                gv.scrollBy(items.size()-1, 0);

                gv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}