package com.example.firebase1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MyGridViewAdapter4 extends BaseAdapter {
    //For StorageActivity.class

    ArrayList<GoodsData> items;
    Context context;

    public MyGridViewAdapter4(ArrayList<GoodsData> items, Context context){
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
        view = inflater.inflate(R.layout.item_storage, viewGroup, false);

        //item_goods에 할인가 정보 삽입
        TextView tv_Itemname = view.findViewById(R.id.tv_Itemname);
        tv_Itemname.setText(items.get(i).itemname);

        //item_goods에 유통기한 정보 삽입
        TextView tv_date = view.findViewById(R.id.tv_date);
        tv_date.setText(items.get(i).date);

        return view;
    }
}
