package com.example.orderfood.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.fragement.BusinessFragmentActivity;
import com.example.orderfood.tools.Tools;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class AddGoodActivity extends AppCompatActivity {

    private ActivityResultLauncher<String> getContentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_good);
        //实现返回功能
        Toolbar toolbar = this.findViewById(R.id.add_business_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String account = sharedPreferences.getString("account","");

        ImageView img = findViewById(R.id.add_business_tx);//头像
        //打开相册上传头像
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery(v);
            }
        });

        getContentLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                //上传头像回显
                if(result != null){
                    Uri uri = (Uri)result;
                    img.setImageURI(uri);
                }else{
                    Toast.makeText(AddGoodActivity.this,"未选择商品图片",Toast.LENGTH_SHORT).show();
                }
            }
        });



        EditText id = findViewById(R.id.add_business_name);//商品名称
        EditText price = findViewById(R.id.add_business_price);//商品价格
        EditText des = findViewById(R.id.add_business_des);//商品描述

        Button add_btn = findViewById(R.id.add_business_btn);//添加按钮


        //获取默认的头像
        Drawable defaultDrawable = ContextCompat.getDrawable(this,R.drawable.upimg);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = id.getText().toString();//商品名字
                String priceText = price.getText().toString();//价格
                String desText = des.getText().toString();//描述

                //获取当前的图的类型
                Drawable drawable = img.getDrawable();

                if(idText.isEmpty()){
                    Toast.makeText(AddGoodActivity.this,"请输入商品名称",Toast.LENGTH_SHORT).show();
                }else if(priceText.isEmpty()){
                    Toast.makeText(AddGoodActivity.this,"请输入商家价格",Toast.LENGTH_SHORT).show();
                }else if(desText.isEmpty()){
                    Toast.makeText(AddGoodActivity.this,"请输入店铺描述",Toast.LENGTH_SHORT).show();
                } else if(drawable instanceof BitmapDrawable){
                    //如果是BitmapDrawable，表示ImageView加载了一张图片
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    //检查Bitmap是否与默认图片相同
                    if(bitmap.sameAs(((BitmapDrawable) defaultDrawable).getBitmap())){
                        Toast.makeText(AddGoodActivity.this,"请点击图片上传头像",Toast.LENGTH_SHORT).show();
                    }else{

                        //获取图片的map
                        Bitmap bitmap1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                        //将Bitmap转换为字节数组
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] imageByteArray = byteArrayOutputStream.toByteArray();

                        //先将头像存储到磁盘，再保留路径
                        String businessTxPath = Tools.getImagePath() + "/" + idText + "A.png";
                        Tools.saveByteArrayAsPng(imageByteArray,businessTxPath,AddGoodActivity.this);

                        //实现自动生成商品的id内容
                        UUID id = UUID.randomUUID();
                        String cid = id.toString().replace("-","");

                        //获取插入结果
                        Long add_user_result = FoodDao.addGood(cid,account,idText,desText,priceText,businessTxPath);
                        if(add_user_result >= 1){
                            Toast.makeText(AddGoodActivity.this,"添加商品成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddGoodActivity.this, BusinessFragmentActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(AddGoodActivity.this,"添加商品失败",Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }
        });

    }

    private void openGallery(View v) {
        //打开相册并等待用户选择图片
        getContentLauncher.launch("image/*");
    }

}