package com.example.orderfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.dao.UserDao;

public class UpdatePwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String account = sharedPreferences.getString("account","");

        Button btn = findViewById(R.id.update_business_pwd_btn);
        EditText one = findViewById(R.id.update_business_pwd_one);
        EditText two = findViewById(R.id.update_business_pwd_two);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oneText = one.getText().toString();
                String twoText = two.getText().toString();
                if (oneText.isEmpty()){
                    Toast.makeText(UpdatePwdActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if(twoText.isEmpty()){
                    Toast.makeText(UpdatePwdActivity.this,"请输入确认密码",Toast.LENGTH_SHORT).show();
                }else if(!oneText.equals(twoText)){
                    Toast.makeText(UpdatePwdActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                }else{
                    int result = UserDao.updateBusinessUserPwd(account,oneText);
                    if(result == 1){
                        Toast.makeText(UpdatePwdActivity.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });
    }
}