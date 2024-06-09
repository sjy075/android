package com.example.orderfood.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.AbstractWindowedCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.orderfood.activity.AddGoodActivity;
import com.example.orderfood.bean.BusinessBean;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.db.DBUtil;

import java.util.ArrayList;

public class UserDao {

    public static SQLiteDatabase db = DBUtil.db;

    //注册普通用户
    public static long addUser(String ...data){
        ContentValues values = new ContentValues();
        values.put("s_id",data[0]);
        values.put("s_pwd",data[1]);
        values.put("s_name",data[2]);
        values.put("s_sex",data[3]);
        values.put("s_address",data[4]);
        values.put("s_phone",data[5]);
        values.put("s_img",data[6]);

        long rowId = db.insert("d_user",null,values);
        return rowId;
    }

    //注册商户
    public static long addBusiness(String ...data){
        ContentValues values = new ContentValues();
        values.put("s_id",data[0]);
        values.put("s_pwd",data[1]);
        values.put("s_name",data[2]);
        values.put("s_describe",data[3]);
        values.put("s_type",data[4]);
        values.put("s_img",data[5]);

        long rowId = db.insert("d_business",null,values);
        return rowId;

    }



    //登录验证   用户名  密码  权限
    public static int loginUser(String account,String pwd,String sta){
        String table = "";
        String sql;
        //1为商家 2为用户
        if(sta.equals("1")){
            sql = "select * from d_business where s_id=? and s_pwd=?";
        }else{
            sql = "select * from d_user where s_id=? and s_pwd=?";
        }

        String values[] = {account,pwd};
        Cursor result= db.rawQuery(sql,values);
        int i = 0;
        while(result.moveToNext()){
            i++;
        }
        return i;
    }

    //根据账号获取商家用户内容
    public static BusinessBean getBusinessUser(String account){
        String table = "";
        String sql = "select * from d_business where s_id=?";
        String values[] = {account};
        Cursor res= db.rawQuery(sql,values);
        int i = 0;
        while(res.moveToNext()){
            String s_id = res.getString(0);
            String s_pwd = res.getString(1);
            String s_name = res.getString(2);
            String s_describe = res.getString(3);
            String s_type = res.getString(4);
            String s_img = res.getString(5);

            BusinessBean businessBean = new BusinessBean(s_id,s_pwd,s_name,s_describe,s_type,s_img);
            return businessBean;

        }
        return null;
    }

    //修改商家密码
    public static int updateBusinessUserPwd(String account,String pwd){
        String sql = "update d_business set s_pwd=? where s_id=?";
        String values[] = {pwd,account};
        try{
            db.execSQL(sql,values);
            return 1;
        }catch (Exception exception){
            return 0;
        }

    }



    //注册商户
    public static long addBusiness(SQLiteDatabase db,String ...data){
        ContentValues values = new ContentValues();
        values.put("s_id",data[0]);
        values.put("s_pwd",data[1]);
        values.put("s_name",data[2]);
        values.put("s_describe",data[3]);
        values.put("s_type",data[4]);
        values.put("s_img",data[5]);

        long rowId = db.insert("d_business",null,values);
        return rowId;

    }

    //注册普通用户
    public static long addUser(SQLiteDatabase db,String ...data){
        ContentValues values = new ContentValues();
        values.put("s_id",data[0]);
        values.put("s_pwd",data[1]);
        values.put("s_name",data[2]);
        values.put("s_sex",data[3]);
        values.put("s_address",data[4]);
        values.put("s_phone",data[5]);
        values.put("s_img",data[5]);

        long rowId = db.insert("d_user",null,values);
        return rowId;

    }






    public static ArrayList<FoodBean> getAllFoodByName(String name) {
        String sql = "select * from d_food where s_food_name like '%" + name + "%'";

        Cursor res = db.rawQuery(sql,null);
        ArrayList<FoodBean> list = new ArrayList<>();
        while(res.moveToNext()){
            String s_food_id = res.getString(0);
            String s_business_id = res.getString(1);
            String s_food_name = res.getString(2);
            String s_food_des = res.getString(3);
            String s_food_price = res.getString(4);
            String s_food_img = res.getString(5);

            FoodBean foodBean = new FoodBean(s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img);
            list.add(foodBean);
        }
        return list;
    }

    //根据商家id获取头像
    public static String getTxByBusinessId(String business_id){

        Cursor res = db.rawQuery("select s_img from d_business where s_id=?",new String[]{business_id});

        while(res.moveToNext()){
            String s_img = res.getString(0);
            return s_img;
        }
        return null;
    }

    //根据商家id获取名称
    public static String getNameByBusinessId(String business_id){

        Cursor res = db.rawQuery("select s_name from d_business where s_id=?",new String[]{business_id});

        while(res.moveToNext()){
            String s_name = res.getString(0);
            return s_name;
        }
        return null;
    }




}
















