//package com.example.orderfood.listener;
//
//
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.graphics.PorterDuffColorFilter;
//import android.graphics.drawable.Drawable;
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.example.orderfood.activity.AddGoodActivity;
//
////设置按钮颜色的监听
//public class ButtonColorListener implements View.OnClickListener{
//
//    Button home,add,my;
//    int sta;
//    public  ButtonColorListener(Button home,Button add,Button my,int sta){
//        this.home = home;
//        this.add = add;
//        this.my = my;
//        this.sta = sta;
//    }
//
//    @Override
//    public void onClick(View v) {
//
//        Button buttons[] = {home,add,my};
//        //循环获取三个按钮，并将按钮上图片设置为灰色
//        for(int i = 1;i <= 3;i++){
//            Button temp = buttons[i - 1];
//            Drawable drawableTop = temp.getCompoundDrawablesRelative()[1];
//            int color = Color.parseColor("#A2A2A2");
//            drawableTop.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
//            temp.setCompoundDrawablesRelative(null,drawableTop,null,null);
//        }
//
//        //获取被点击的对象
//        Button a = (Button)v;
//        //获取RadioButton的CompoundDrawableTop
//        Drawable drawableTop = a.getCompoundDrawablesRelative()[1];
//        //创建一个过滤器设置颜色
//        int color = Color.parseColor("#F24960");
//        drawableTop.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
//        //将修改后的Drawable设置回RadioButton
//        a.setCompoundDrawablesRelative(null,drawableTop,null,null);
//
//
//    }
//}
