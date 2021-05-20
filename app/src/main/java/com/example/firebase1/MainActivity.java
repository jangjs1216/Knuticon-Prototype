package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //네비게이션 메뉴 구현
    DrawerLayout drawerLayout;
    View drawerView;
    String name;

    //데이터베이스 연동
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    private FirebaseAuth mFirebaseAuth;

    //데이터베이스 추가용
    private DatabaseReference addDatabase;
    private String userUrl = "";

    //Compare Key
    ArrayList<String> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //값을 출력할 TextView
        TextView user_data = (TextView)findViewById(R.id.user_data);

        //B. 데이터베이스에서 값 가져오는 구문
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        if(firebaseUser == null)
            Toast.makeText(MainActivity.this, "데이터베이스 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivity.this, "데이터베이스 연결에 성공하였습니다.", Toast.LENGTH_SHORT).show();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(firebaseUser == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }else {
            DocumentReference docRef = db.collection("users").document(firebaseUser.getUid());
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            user_data.setText(document.getData().toString());
                            Toast.makeText(MainActivity.this, "데이터베이스 다운로드에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "데이터베이스 다운로드에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "데이터베이스 연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        Button btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃 하기
                mFirebaseAuth.signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });

        // 탈퇴 처리
        // mFirebaseAuth.getCurrentUser().delete();

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

        Button btn_open = (Button)findViewById(R.id.btn_open);
        // 네비게이션 메뉴 열기
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        Button btn_close = (Button)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    public void category_menu(View v){
        // 카테고리 선택
        int id = v.getId();
        TextView tv = (TextView)findViewById(id);
        String category = (String)tv.getText();
        String tag = (String)tv.getTag();

        Toast.makeText(MainActivity.this, tag + "를 선택하였습니다.", Toast.LENGTH_SHORT).show();

        // 태그 값 씌워서 인텐트로 보내기
        Intent it = new Intent(this, CategoryActivity.class);
        it.putExtra("it_category", category);
        it.putExtra("level1",tag);
        startActivity(it);
    }

    public void insert_goods(View v){
        final FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
        userUrl = user.getUid();

        addDatabase = FirebaseDatabase.getInstance().getReference("category").child("cafe").child("starbucks");

        String name = "test";
        addDatabase.push().child(name);
        addDatabase.child(name).child("name").setValue(name);
    }

    public void get_goods(View v) throws IOException {
        GetDatabase getDatabase = new GetDatabase();
        getDatabase.print(reference.child("category").child("cafe").child("starbucks"));

        ImageView iv_starbucks = (ImageView)findViewById(R.id.iv_starbucks);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference spaceRef = storage.getReference("starbucks.jpg");
        spaceRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    // Glide 이용하여 이미지뷰에 로딩
                    Glide.with(MainActivity.this)
                            .load(task.getResult())
                            .override(1024, 980)
                            .into(iv_starbucks);
                } else {
                    // URL을 가져오지 못하면 토스트 메세지
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
//        Log.d("###", spaceRef.toString());
//        Glide.with(this)
//                .load(spaceRef)
//                .centerCrop()
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(iv_starbucks);
    }

    public void generate_key(View v){
        final FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
        userUrl = user.getUid();

        addDatabase = FirebaseDatabase.getInstance().getReference("Storage");

        //현재 키를 발급
        String MyKey = user.getUid();
        String RandomKey = addDatabase.push().getKey();
        addDatabase.child(RandomKey).child("owner").setValue(MyKey);
        addDatabase.child(RandomKey).child("Itemname").setValue(String.valueOf(Math.random()));
    }

    public void compare_key(View v){
        reference.child("Storage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GridView gv = (GridView)findViewById(R.id.gv_goods);

                items.clear();
                for (final DataSnapshot data : snapshot.getChildren()) {
                    Log.d("cmp", "Running");
                    Log.d("cmp", data.getValue().toString());
                    items.add(data.getValue().toString());
                    for(final String s : data.getValue())
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}