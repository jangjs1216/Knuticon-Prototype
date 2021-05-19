package com.example.firebase1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyGridViewAdapter extends BaseAdapter {
    ArrayList<String> items;
    Context context;

    public MyGridViewAdapter(ArrayList<String> items, Context context){
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Object getItem(int i){
        return items.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.item_category, viewGroup, false);

        TextView tv_name = view.findViewById(R.id.tv_categoryname);
        tv_name.setText(items.get(i));

        return view;
    }
}
