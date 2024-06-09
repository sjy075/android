package com.example.orderfood.fragement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.orderfood.R;
import com.example.orderfood.activity.AddGoodActivity;
import com.example.orderfood.activity.BusinessActivityFragment;
import com.example.orderfood.activity.BusinessMyActivityFragment;


public class BusinessFragmentActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private BusinessActivityFragment f1;
    private BusinessMyActivityFragment f2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_fragement);
        fragmentManager = getSupportFragmentManager();
        f1 = new BusinessActivityFragment();
        f2 = new BusinessMyActivityFragment();
        //设置界面首页
        fragmentManager.beginTransaction().replace(R.id.frame_layout,f1).commit();

        Button home = this.findViewById(R.id.business_home_radio);
        Button add = this.findViewById(R.id.business_add_radio);
        Button my = this.findViewById(R.id.business_my_radio);
        setButtonRed(home);
        setButtonGray(my);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager.beginTransaction().replace(R.id.frame_layout,f1).commit();
                setButtonRed(home);
                setButtonGray(my);
            }
        });

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragmentManager.beginTransaction().replace(R.id.frame_layout,f2).commit();
                setButtonRed(my);
                setButtonGray(home);

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BusinessFragmentActivity.this, AddGoodActivity.class);
                startActivity(intent);

            }
        });
    }

    private void setButtonRed(View view){
        Button a = (Button)view;
        //获取RadioButton的CompoundDrawableTop
        Drawable drawableTop = a.getCompoundDrawablesRelative()[1];
        //创建一个过滤器设置颜色
        int color = Color.parseColor("#F24960");
        drawableTop.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        //将修改后的Drawable设置回RadioButton
        a.setCompoundDrawablesRelative(null,drawableTop,null,null);
    }

    private void setButtonGray(View view){
        Button a = (Button)view;
        //获取RadioButton的CompoundDrawableTop
        Drawable drawableTop = a.getCompoundDrawablesRelative()[1];
        //创建一个过滤器设置颜色
        int color = Color.parseColor("#A2A2A2");
        drawableTop.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        //将修改后的Drawable设置回RadioButton
        a.setCompoundDrawablesRelative(null,drawableTop,null,null);
    }
}