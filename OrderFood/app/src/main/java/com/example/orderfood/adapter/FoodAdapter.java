package com.example.orderfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;


import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.UserDao;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<FoodBean> {
    private ArrayList<FoodBean> list;

    public FoodAdapter(@NonNull Context context,ArrayList<FoodBean> list){
        super(context,R.layout.food_list,list);
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.food_list,parent,false);
        }
        TextView name = convertView.findViewById(R.id.food_list_name);
        TextView price = convertView.findViewById(R.id.food_list_price);
        TextView des = convertView.findViewById(R.id.food_list_des);
        ImageView img = convertView.findViewById(R.id.food_list_img);

        FoodBean foodbean = list.get(position);
        name.setText(foodbean.getS_food_name());

        price.setText("价格:" + foodbean.getS_food_price() + "元");
        des.setText("描述:" + foodbean.getS_food_des());

        Bitmap bitmap = BitmapFactory.decodeFile(foodbean.getS_food_img());
        img.setImageBitmap(bitmap);




        return convertView;
    }


}
