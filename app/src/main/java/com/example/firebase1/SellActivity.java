package com.example.firebase1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SellActivity extends AppCompatActivity {

    //Storage 연동
    FirebaseStorage storage = FirebaseStorage.getInstance();

    //Firebase Reference
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    private FirebaseAuth mFirebaseAuth;

    //데이터베이스 추가용
    private DatabaseReference addDatabase;
    private String userUrl = "";

    //metadata
    String level1, level2, level3, price;

    //날짜 정보
    TextView tv_date_view;

    TextView tv_sell_price;
    EditText tv_sell_discount;

    // 12.18 추가
    EditText tv_sell_name, tv_sell_desc;

    ImageView imageView;

    //ImageView 가져오기용
    public int REQUEST_CODE = 0;
    public int REQUEST_CODE_DATE = 1;

    //판매시 파일 검사
    boolean isPicture = false;
    boolean isDate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);

        //날짜 정보 연동하기
        tv_date_view = (TextView)findViewById(R.id.tv_date_view);

        //ImageView 정보 가져오기
        imageView = (ImageView)findViewById(R.id.iv_gifticon);

        //Intent 정보 가져오기
        Intent it = getIntent();
        level1 = it.getStringExtra("level1");
        level2 = it.getStringExtra("level2");
        level3 = it.getStringExtra("level3");
        price = it.getStringExtra("price");

        //텍스트 정보 가져오기
        tv_sell_discount = (EditText)findViewById(R.id.tv_sell_discount);
        tv_sell_price = (TextView)findViewById(R.id.tv_sell_price);
        tv_sell_name = (EditText)findViewById(R.id.tv_sell_name);
        tv_sell_desc = (EditText)findViewById(R.id.tv_sell_desc);

        //정가 가져오기
        tv_sell_price.setText(price);
    }

    public void sell_goods(View v){
        if(isDate && isPicture && Integer.parseInt(tv_sell_discount.getText().toString()) < Integer.parseInt(tv_sell_price.getText().toString()))
        {
            final FirebaseUser user = mFirebaseAuth.getInstance().getCurrentUser();
            userUrl = user.getUid();

            //날짜 가져오기
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

            //데이터베이스 연동
            addDatabase = FirebaseDatabase.getInstance().getReference("category").child(level1).child(level2).child(level3);

            //텍스트뷰 값 가져오기
            String name = tv_sell_discount.getText().toString();

            Toast.makeText(getApplicationContext(), price, Toast.LENGTH_LONG).show();

            String key = addDatabase.push().getKey();

            //GoodsData 객체 생성
            GoodsData goodsData = new GoodsData(
                    Integer.parseInt(tv_sell_discount.getText().toString()),
                    Integer.parseInt(tv_sell_price.getText().toString()),
                    tv_sell_name.getText().toString(),
                    tv_date_view.getText().toString(),
                    user.getEmail(),
                    user.getUid(),
                    key,
                    tv_sell_desc.getText().toString()
            );

            Log.e("###", "유통기한 정보 : "+tv_date_view.getText().toString());

            addDatabase.child("goods").child(key).setValue(goodsData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        //삽입이 완료되면 할 행동
                    }
                }
            });

            //imageView를 bitmap화 한 다음, Output Stream으로 변환.
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            StorageReference storageRef = storage.getReference();
            StorageReference mountainsRef = storageRef.child(key+".jpg");
            UploadTask uploadTask = mountainsRef.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Log.d("###", "사진 삽입 실패");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    Log.d("###", "사진 삽입 성공");
                }
            });

            finish();
        }else{
            if(!isPicture)
                Toast.makeText(getApplicationContext(), "기프티콘을 첨부해주세요!", Toast.LENGTH_SHORT).show();
            else if(!isDate)
                Toast.makeText(getApplicationContext(), "날짜정보를 입력해주세요!", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplicationContext(), "가격을 정확히 입력해주세요!", Toast.LENGTH_SHORT).show();
        }
    }

    public void sell_pics(View v){
        Intent intent = new Intent();
        intent.setType("image/*");

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    // 유통기한 선택하기
    public void select_date(View v){

        Intent intent = new Intent(this, DatePickerActivity.class);
        startActivityForResult(intent, REQUEST_CODE_DATE);
    }

    //사진 갤러리에서 가져오기 / 서버에 업로드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("###", "Seller intent 호출됨!");
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    isPicture = true;

                    imageView.setImageBitmap(img);
                } catch (Exception e) {

                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }else if(requestCode == REQUEST_CODE_DATE){
            if (resultCode == RESULT_OK){
                Log.e("###", "Date intent 호출됨!");

                String yy = data.getExtras().get("mYear").toString();
                String mm = data.getExtras().get("mMonth").toString();
                //달 처리
                int monthInt = Integer.parseInt(mm);
                if(monthInt < 9) {
                    mm = ".0"+String.valueOf(monthInt + 1);
                }else{
                    mm = "."+String.valueOf(monthInt + 1);
                }

                String dd = "."+data.getExtras().get("mDay").toString();

                isDate = true;

                Toast.makeText(this, yy+mm+dd+"로 선택하셨습니다.", Toast.LENGTH_LONG).show();

                tv_date_view.setText(yy+mm+dd);
            }
        }
    }
}