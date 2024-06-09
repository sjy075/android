package com.example.orderfood.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.bean.BusinessBean;
import com.example.orderfood.dao.UserDao;


public class BusinessMyActivityFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_business_my, container, false);
        Activity activity = getActivity();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String account = sharedPreferences.getString("account", "");
        //加载头像
        ImageView tx = view.findViewById(R.id.business_my_tx);
        //根据账号查看账号信息
        BusinessBean user = UserDao.getBusinessUser("root");
        //实现头像的显示
        Bitmap bitmap = BitmapFactory.decodeFile(user.getS_img());
        tx.setImageBitmap(bitmap);
        //id
        TextView id = view.findViewById(R.id.business_my_id);
        id.setText(user.getS_id());
        //名称
        TextView name = view.findViewById(R.id.business_my_name);
        name.setText(user.getS_name());
        //描述
        TextView des = view.findViewById(R.id.business_my_des);
        des.setText("店铺简介:" + user.getS_describe());
        //退出功能
        TextView exit = view.findViewById(R.id.business_my_exit);


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finishAffinity();
            }
        });

        //注销功能
        TextView zx = view.findViewById(R.id.business_my_zx);
        zx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
        });

        TextView xgmm = view.findViewById(R.id.business_my_xgmm);
        //修改密码
        xgmm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, UpdatePwdActivity.class);
                activity.startActivity(intent);
            }
        });










    return view;
    }

}