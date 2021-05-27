package com.example.firebase1;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserInfo {
    private FirebaseAuth mFirebaseAuth;

    String emailId;
    String idToken;
    String password;
    Long point;

    UserInfo(String userKey)
    {
        emailId = "";
        idToken = "";
        password = "";
        point = Long.valueOf(0);
        getUserInfo(userKey);
    }

    UserInfo(String emailId, String idToken, String password, Long point)
    {
        this.emailId = emailId;
        this.idToken = idToken;
        this.password = password;
        this.point = point;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public void getUserInfo(String userKey){
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        //판매자의 현재 Point 값 가져오기
        Log.e("###", "현재 판매자 : " + userKey);
        DocumentReference sellerRef = db.collection("users").document(userKey);
        sellerRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document != null && document.exists()) {
                        emailId = document.get("emailId").toString();
                        idToken = document.get("idToken").toString();
                        password = document.get("password").toString();
                        point = Long.valueOf(document.get("point").toString());

                        Log.e("###", point.toString());
                    }
                }
            }
        });
    }

    public void setUserPoint(String userKey, Long point)
    {
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference sellerRef = db.collection("users").document(userKey);

        sellerRef
                .update("point", point)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Buy###", "DocumentSnapshot successfully updated! Point : "+point);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Buy###", "Error updating document", e);
                    }
                });
    }
}
