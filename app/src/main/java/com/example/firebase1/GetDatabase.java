package com.example.firebase1;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GetDatabase {
    public void print(DatabaseReference reference) {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot data : snapshot.getChildren()) {
                    for (final DataSnapshot info : data.getChildren()) {
                        String name = info.getValue().toString();
                        Log.d("####", "String name은: " + name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public ArrayList<String> get(DatabaseReference reference){
        ArrayList<String> ret = new ArrayList<String>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (final DataSnapshot data : snapshot.getChildren()) {
                    String cafeName = data.getKey();
                    Log.d("####", "String name은: " + cafeName);
                    ret.add(cafeName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return ret;
    }
}
