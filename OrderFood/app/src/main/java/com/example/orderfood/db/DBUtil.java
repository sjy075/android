package com.example.orderfood.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.tools.Tools;

import java.io.ByteArrayOutputStream;

public class DBUtil extends SQLiteOpenHelper {

    //数据库名称
    private static final String DB_NAME = "db_food.db";

    //通过db来操作数据库
    public static SQLiteDatabase db = null;

    private static final int VERSION = 17;//数据库的版本

    private Context context;



    public DBUtil(Context context){
        super(context,DB_NAME,null,VERSION,null);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //取消外键
        db.execSQL("PRAGMA foreign_keys = false");



        //创建商家表
        db.execSQL("drop table if exists d_business");//如果存在商家表则删除
        //商家ID，密码，店名，描述，类型，图片
        db.execSQL("create table d_business(s_id varchar(20) primary key," +
                "s_pwd varchar(20)," +
                "s_name varchar(20)," +
                "s_describe varchar(20)," +
                "s_type varchar(20)," +
                "s_img varchar(255))");


        //创建用户表
        db.execSQL("drop table if exists d_user");//如果存在用户表则删除
        //用户账号，密码，用户名，性别，地址，电话，头像
        db.execSQL("create table d_user(s_id varchar(20) primary key," +
                "s_pwd varchar(20)," +
                "s_name varchar(20)," +
                "s_sex varchar(20)," +
                "s_address varchar(20)," +
                "s_phone varchar(20)," +
                "s_img varchar(255))");

        //建立一个存储商品的表
        db.execSQL("drop table if exists d_food");//如果存在商品表则删除
        //食物id 店铺id 食物名称 食物描述 食物价格 食物图片
        db.execSQL("create table d_food(s_food_id varchar(20) primary key," +
                "s_business_id varchar(20)," +
                "s_food_name varchar(20)," +
                "s_food_des varchar(20)," +
                "s_food_price varchar(20)," +
                "s_food_img varchar(255))");


        db.execSQL("PRAGMA foreign_keys = true");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        onCreate(sqLiteDatabase);
    }

    //获取图片的字节流
    private byte[] getImg(int value){

        Resources res = context.getResources();//获取资源
        Bitmap bitmap = BitmapFactory.decodeResource(res, value);//获取图片map
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();//将Bitmap转换为字节数组
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] imageByteArray = byteArrayOutputStream.toByteArray();

        return imageByteArray;

    }
}
