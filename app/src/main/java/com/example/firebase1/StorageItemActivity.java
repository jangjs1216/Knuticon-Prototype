package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StorageItemActivity extends AppCompatActivity {
    //데이터베이스 연동
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    private FirebaseAuth mFirebaseAuth;

    String gifticon_uri;
    String date;

    Button btn_over;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_item);

        Intent it = getIntent();
        gifticon_uri = it.getStringExtra("gifticon_uri");
        date = it.getStringExtra("date");

        //ImageView 아이디 가져오기
        ImageView iv_storage_gifticon = (ImageView)findViewById(R.id.iv_storage_gifticon);

        GetDatabase getDatabase = new GetDatabase();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference spaceRef = storage.getReference(gifticon_uri+".jpg");
        spaceRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // Glide 이용하여 이미지뷰에 로딩
                    Glide.with(getApplicationContext())
                            .load(task.getResult())
                            .override(1024, 980)
                            .into(iv_storage_gifticon);
                } else {
                    // URL을 가져오지 못하면 토스트 메세지
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.d("###", spaceRef.toString());
        Glide.with(this)
                .load(spaceRef)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(iv_storage_gifticon);

        btn_over = (Button)findViewById(R.id.btn_over);
        btn_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
}