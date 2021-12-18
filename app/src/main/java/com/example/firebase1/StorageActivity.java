package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StorageActivity extends AppCompatActivity {

    //Firebase Reference
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    //GridView Adapter
    MyGridViewAdapter4 adapter;
    ArrayList<GoodsData> items = new ArrayList<>();

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        reference.child("storage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();

                GridView gv = (GridView)findViewById(R.id.gv_storage);

                items.clear();
                for (final DataSnapshot data : snapshot.getChildren()) {
                    GoodsData gds = data.getValue(GoodsData.class);
                    items.add(gds);
                }

                adapter = new MyGridViewAdapter4(items, getApplicationContext());
                gv.setAdapter(adapter);

                int count = 0;
                for(GoodsData gds : items)
                {
                    if(gds.owner.equals(user.getUid()))
                        count++;
                }

                gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String item_name = (String) adapter.getItem(position).toString();
                        Toast.makeText(getApplicationContext(), item_name+"이 아이템이 선택되었습니다", Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(getApplicationContext(), StorageItemActivity.class);
                        it.putExtra("gifticon_uri", String.valueOf(items.get(position).gifticon_uri));
                        it.putExtra("date", String.valueOf(items.get(position).date));

                        startActivity(it);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}