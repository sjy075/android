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

//注册用户界面
public class RegisteredUsers extends AppCompatActivity {

    private ActivityResultLauncher<String> getContentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_users);

        //实现返回功能
        Toolbar toolbar = this.findViewById(R.id.reg_user_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView img = findViewById(R.id.reg_user_tx);//头像
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
                    Toast.makeText(RegisteredUsers.this,"未上传头像",Toast.LENGTH_SHORT).show();
                }
            }
        });




        EditText id = findViewById(R.id.reg_user_id);//账号
        EditText pwd = findViewById(R.id.reg_user_pwd);//密码
        EditText name = findViewById(R.id.reg_user_name);//昵称
        RadioButton man = findViewById(R.id.reg_user_man);//男
        RadioButton woman = findViewById(R.id.reg_user_woman);//女
        man.setChecked(true);//设置默认为男
        EditText address = findViewById(R.id.reg_user_address);//地址
        EditText phone = findViewById(R.id.reg_user_phone);//手机号
        Button reg_btn = findViewById(R.id.reg_user_btn);//注册按钮

        //获取默认的头像
        Drawable defaultDrawable = ContextCompat.getDrawable(this,R.drawable.upimg);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idText = id.getText().toString();
                String pwdText = pwd.getText().toString();
                String nameText = name.getText().toString();
                String addressText = address.getText().toString();
                String phoneText = phone.getText().toString();

                //获取当前的图的类型
                Drawable drawable = img.getDrawable();

                if(idText.isEmpty()){
                    Toast.makeText(RegisteredUsers.this,"请输入用户账号",Toast.LENGTH_SHORT).show();
                }else if(pwdText.isEmpty()){
                    Toast.makeText(RegisteredUsers.this,"请输入用户密码",Toast.LENGTH_SHORT).show();
                }else if(nameText.isEmpty()){
                    Toast.makeText(RegisteredUsers.this,"请输入用户昵称",Toast.LENGTH_SHORT).show();
                }else if(addressText.isEmpty()){
                    Toast.makeText(RegisteredUsers.this,"请输入用户地址",Toast.LENGTH_SHORT).show();
                }else if(phoneText.isEmpty()){
                    Toast.makeText(RegisteredUsers.this,"请输入用户联系方式",Toast.LENGTH_SHORT).show();
                }else if(drawable instanceof BitmapDrawable){
                    //将当前图片转换为Bitmap类型
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    //检查Bitmap是否与默认图片相同
                    if(bitmap.sameAs(((BitmapDrawable) defaultDrawable).getBitmap())){
                        Toast.makeText(RegisteredUsers.this,"请点击图片上传头像",Toast.LENGTH_SHORT).show();
                    }else{
                        //获取图片的map
                        Bitmap bitmap1 = ((BitmapDrawable)img.getDrawable()).getBitmap();
                        //将Bitmap转换为字节数组
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//获取图片map
                        bitmap1.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);//将Bitmap转换为字节数组
                        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
                        String sex = "女";
                        if(man.isChecked()){
                            sex = "男";
                        }
                        //先将头像存储到磁盘，再保留路径
                        String userTxPath = Tools.getImagePath() + "/" + idText + ".png";
                        Tools.saveByteArrayAsPng(imageByteArray,userTxPath,RegisteredUsers.this);

                        //获取插入结果
                        Long reg_user_result = UserDao.addUser(idText,pwdText,nameText,sex,addressText,phoneText,userTxPath);
                        if(reg_user_result >= 1){
                            Toast.makeText(RegisteredUsers.this,"用户注册成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(RegisteredUsers.this,"用户注失败，账号已存在",Toast.LENGTH_SHORT).show();
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




