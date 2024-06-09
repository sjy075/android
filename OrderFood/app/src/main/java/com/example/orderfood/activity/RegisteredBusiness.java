package com.example.orderfood.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class RegisteredBusiness extends AppCompatActivity {

    private ActivityResultLauncher<String> getContentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_business);

        //实现返回功能
        Toolbar toolbar = this.findViewById(R.id.reg_business_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView img = findViewById(R.id.reg_business_tx);//头像
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
                    Toast.makeText(RegisteredBusiness.this,"未上传头像",Toast.LENGTH_SHORT).show();
                }
            }
        });



        EditText id = findViewById(R.id.reg_business_id);//账号
        EditText pwd = findViewById(R.id.reg_business_pwd);//密码
        EditText name = findViewById(R.id.reg_business_name);//昵称

        EditText des = findViewById(R.id.reg_business_des);//描述
        EditText type = findViewById(R.id.reg_business_type);//类型
        Button reg_btn = findViewById(R.id.reg_business_btn);//注册按钮

        //获取默认的头像
        Drawable defaultDrawable = ContextCompat.getDrawable(this,R.drawable.upimg);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = id.getText().toString();
                String pwdText = pwd.getText().toString();
                String nameText = name.getText().toString();
                String desText = des.getText().toString();
                String typeText = type.getText().toString();

                //获取当前的图的类型
                Drawable drawable = img.getDrawable();

                if(idText.isEmpty()){
                    Toast.makeText(RegisteredBusiness.this,"请输入商家账号",Toast.LENGTH_SHORT).show();
                }else if(pwdText.isEmpty()){
                    Toast.makeText(RegisteredBusiness.this,"请输入商家密码",Toast.LENGTH_SHORT).show();
                }else if(nameText.isEmpty()){
                    Toast.makeText(RegisteredBusiness.this,"请输入店铺昵称",Toast.LENGTH_SHORT).show();
                }else if(desText.isEmpty()){
                    Toast.makeText(RegisteredBusiness.this,"请输入店铺描述",Toast.LENGTH_SHORT).show();
                }else if(typeText.isEmpty()){
                    Toast.makeText(RegisteredBusiness.this,"请输入店铺类型",Toast.LENGTH_SHORT).show();
                }else if(drawable instanceof BitmapDrawable){
                    //将当前图片转换为Bitmap类型
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    //检查Bitmap是否与默认图片相同
                    if(bitmap.sameAs(((BitmapDrawable) defaultDrawable).getBitmap())){
                        Toast.makeText(RegisteredBusiness.this,"请点击图片上传头像",Toast.LENGTH_SHORT).show();
                    }else{
                        //获取图片的bitmap
                        Bitmap bitmap1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                        //将Bitmap压缩为字节数组
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        //将bitmap1按照png格式进行压缩并写入byteArrayOutputStream，100为压缩质量，表示不压缩
                        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        //将压缩后的数据转换为字节数组
                        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
                        //先将头像存储到磁盘，再保留路径
                        String businessTxPath = Tools.getImagePath() + "/" + idText + "A.png";
                        Tools.saveByteArrayAsPng(imageByteArray,businessTxPath,RegisteredBusiness.this);

                        //获取插入结果
                        Long reg_user_result = UserDao.addBusiness(idText,pwdText,nameText,desText,typeText,businessTxPath);
                        if(reg_user_result >= 1){
                            Toast.makeText(RegisteredBusiness.this,"店铺注册成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(RegisteredBusiness.this,"店铺注册失败，账号已存在",Toast.LENGTH_SHORT).show();
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