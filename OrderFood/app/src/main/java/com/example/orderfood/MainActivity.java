package com.example.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.orderfood.fragement.BusinessFragmentActivity;
import com.example.orderfood.activity.BuyFoodActivity;
import com.example.orderfood.activity.RegisteredBusiness;
import com.example.orderfood.activity.RegisteredUsers;
import com.example.orderfood.borad.WifiReceiver;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.db.DBUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //调用连接数据库
        DBUtil dbUtil = new DBUtil(MainActivity.this);
        SQLiteDatabase db = dbUtil.getWritableDatabase();//获取连接
        DBUtil.db = db;


        //调试
//        Intent intent = new Intent(MainActivity.this, UserManageActivity.class);
//        Intent intent = new Intent(MainActivity.this, BusinessFragementActivity.class);
//        startActivity(intent);

        //创建WifiReceiver对象
        WifiReceiver wifiReceiver = new WifiReceiver();
        //创建IntentFilter对象，并为其添加两个动作
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        //注册广播接收器，并指定intentFilter过滤器
        registerReceiver(wifiReceiver, intentFilter);


        //获取商家和用户按钮，默认选择注册商家
        RadioButton sj_radio = this.findViewById(R.id.login_sj_radio);
        RadioButton user_radio = this.findViewById(R.id.login_user_radio);
        sj_radio.setChecked(true);

        //获取商家和用户注册按钮
        Button sj_btn = this.findViewById(R.id.login_sj_btn);
        Button user_btn = this.findViewById(R.id.login_user_btn);
        //商家按钮，跳转到商家注册页面
        sj_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisteredBusiness.class);
                startActivity(intent);
            }
        });
        //用户按钮，跳转到用户注册页面
        user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisteredUsers.class);
                startActivity(intent);
            }
        });

        //获取用户名和密码
        EditText id = this.findViewById(R.id.login_id);
        EditText pwd = this.findViewById(R.id.login_pwd);
        //获取登录按钮，
        Button login_btn = this.findViewById(R.id.login_btn);
        //登录功能
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取用户名和密码的文本
                String idText = id.getText().toString();
                String pwdText = pwd.getText().toString();
                //如果用户名和密码为空则提示
                if(idText.isEmpty()){
                    Toast.makeText(MainActivity.this,"请输入账号",Toast.LENGTH_SHORT).show();
                }else if(idText.isEmpty()){
                    Toast.makeText(MainActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else{
                    //创建一个SharedPreferences对象，令其文件名为data
                    SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    //获取Editor对象
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //将账号进行存储
                    editor.putString("account",idText);
                    editor.apply();

                    //判断选择了什么登录
                    if(sj_radio.isChecked()){
                         //调用UserDao的login功能
                         int result = UserDao.loginUser(idText,pwdText,"1");
                         if(result == 0){
                             //如果账号密码错误则提示
                             Toast.makeText(MainActivity.this,"账号或密码错误，登录失败",Toast.LENGTH_SHORT).show();
                         }else{
                             //成功则跳转到商家页面
                             Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(MainActivity.this, BusinessFragmentActivity.class);
                             startActivity(intent);
                         }
                    }else{
                        //调用UserDao的login功能
                        int result = UserDao.loginUser(idText,pwdText,"2");
                        if(result == 0){
                            //如果账号密码错误则提示
                            Toast.makeText(MainActivity.this,"账号或密码错误，登录失败",Toast.LENGTH_SHORT).show();
                        }else{
                            //成功则跳转到用户购物界面
                            Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, BuyFoodActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });


    }
}


















