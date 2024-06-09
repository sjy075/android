package com.example.orderfood.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.TTS.TextToSpeechUtil;
import com.example.orderfood.activity.BuyFoodActivity;
//import com.example.orderfood.activity.UpdateGoodActivity;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.UserDao;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class BuyFoodAdapter extends ArrayAdapter<FoodBean> {
    private ArrayList<FoodBean> list;

    private Context context;

    private TextToSpeechUtil tts;

    public BuyFoodAdapter(@NonNull Context context, ArrayList<FoodBean> list){
        super(context,R.layout.buy_food_list,list);
        this.list = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.buy_food_list,parent,false);
        }
        //获取控件
        TextView name = convertView.findViewById(R.id.food_list_name);
        TextView price = convertView.findViewById(R.id.food_list_price);
        TextView des = convertView.findViewById(R.id.food_list_des);
        TextView id = convertView.findViewById(R.id.food_list_name_id);
        ImageView img = convertView.findViewById(R.id.food_list_img);
        ImageView business_img = convertView.findViewById(R.id.business_buy_tx);
        TextView business_name = convertView.findViewById(R.id.business_buy_name);

        //获取列表中位置
        FoodBean foodbean = list.get(position);
        name.setText(foodbean.getS_food_name());


        id.setText(foodbean.getS_food_id());
        price.setText("价格:" + foodbean.getS_food_price() + "元");
        des.setText("描述:" + foodbean.getS_food_des());

        //获取食物照片并设置
        Bitmap bitmap = BitmapFactory.decodeFile(foodbean.getS_food_img());
        img.setImageBitmap(bitmap);

        //获取商家头像并设置
        Bitmap bitmap1 = BitmapFactory.decodeFile(UserDao.getTxByBusinessId(foodbean.getS_business_id()));
        business_img.setImageBitmap(bitmap1);

        //获取商家名称并设置
        business_name.setText(UserDao.getNameByBusinessId(foodbean.getS_business_id()));


        TextView add = convertView.findViewById(R.id.user_food_list_jiahao);
        TextView sub = convertView.findViewById(R.id.user_food_list_jianhao);
        TextView num = convertView.findViewById(R.id.user_food_list_num);
        View view = ((Activity) context).findViewById(R.id.user_food_list_total);
        TextView total_price = (TextView)view;

        //创建tts对象
        tts = new TextToSpeechUtil(getContext());
        ImageButton bf = convertView.findViewById(R.id.bf);
        bf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToSpeak = "食物名称" + foodbean.getS_food_name() + price.getText().toString() + "店铺名称" + business_name.getText().toString() + des.getText().toString(); // 指定的文字
                tts.speak(textToSpeak);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(String.valueOf(Integer.parseInt(num.getText().toString()) + 1));
                double sl = Double.valueOf(num.getText().toString()) ;
                double price = Double.valueOf(foodbean.getS_food_price());
                double now_total = Double.valueOf(total_price.getText().toString()) + price;
                double round_total = Math.round(now_total * 100.0) / 100.0;
                total_price.setText(String.valueOf(round_total));



            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(num.getText().toString()) == 0){
                    num.setText("0");
                    return;
                }
                num.setText(String.valueOf(Integer.parseInt(num.getText().toString()) - 1));
                double sl = Double.valueOf(num.getText().toString()) ;
                double price = Double.valueOf(foodbean.getS_food_price());
                double now_total = Double.valueOf(total_price.getText().toString()) - price;
                double round_total = Math.round(now_total * 100.0) / 100.0;
                total_price.setText(String.valueOf(round_total));


            }
        });



        return convertView;
    }


}
