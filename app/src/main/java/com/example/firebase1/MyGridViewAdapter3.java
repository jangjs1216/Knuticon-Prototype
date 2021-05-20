package com.example.firebase1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyGridViewAdapter3 extends BaseAdapter {
    ArrayList<GoodsData> items;
    Context context;

    public MyGridViewAdapter3(ArrayList<GoodsData> items, Context context){
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_goods, viewGroup, false);

        //item_goods에 할인가 정보 삽입
        TextView tv_discount = view.findViewById(R.id.tv_discount);
        tv_discount.setText(String.valueOf(items.get(i).discount));

        //날짜 가져오기
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

        //tv_goods에 정가 정보 삽입
        TextView tv_price = view.findViewById(R.id.tv_price);

        //할인율 구하기
        double percent = (double)(1.0-(double)items.get(i).discount/items.get(i).price)*100.0;
        tv_price.setText("-"+String.format("%.0f",percent)+"%");

        //item_goods에 유통기한 정보 삽입
        TextView tv_date = view.findViewById(R.id.tv_date);
        tv_date.setText(items.get(i).date);

        return view;
    }
}
