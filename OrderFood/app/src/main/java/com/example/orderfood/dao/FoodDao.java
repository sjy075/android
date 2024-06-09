package com.example.orderfood.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.db.DBUtil;

import java.util.ArrayList;

public class FoodDao {
    public static SQLiteDatabase db = DBUtil.db;

    public static int delFood(String foodId){
        try{
            db.execSQL("delete from d_food where s_food_id=?",new String[]{foodId});
            return 1;
        }catch (Exception e){
            return 0;
        }

    }

    //获取所有食物信息
    public static ArrayList<FoodBean> getAllFood(){

        Cursor res = db.rawQuery("select * from d_food",null);


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

    //通过食物id进行查询单个食物
    public static FoodBean getFoodById(String id){
        Cursor res = db.rawQuery("select * from d_food where s_food_id=?",new String[]{id});
        FoodBean foodBean = null;
        while(res.moveToNext()){
            String s_food_id = res.getString(0);
            String s_business_id = res.getString(1);
            String s_food_name = res.getString(2);
            String s_food_des = res.getString(3);
            String s_food_price = res.getString(4);
            String s_food_img = res.getString(5);

            foodBean = new FoodBean(s_food_id,s_business_id,s_food_name,s_food_des,s_food_price,s_food_img);
        }
        return foodBean;
    }

    //添加商品
    public static long addGood(String ...data){
        ContentValues values = new ContentValues();
        values.put("s_food_id",data[0]);
        values.put("s_business_id",data[1]);
        values.put("s_food_name",data[2]);
        values.put("s_food_des",data[3]);
        values.put("s_food_price",data[4]);
        values.put("s_food_img",data[5]);

        long rowId = db.insert("d_food",null,values);
        return rowId;

    }

    //更改商品内容
    public static long updateGood(String ...data){

        ContentValues values = new ContentValues();
        values.put("s_food_name",data[0]);
        values.put("s_food_price",data[1]);
        values.put("s_food_des",data[2]);
        values.put("s_food_img",data[3]);
        long rowId = db.update("d_food",values,"s_food_id=?",new String[]{data[4]});
        return rowId;

    }

    public static ArrayList<FoodBean> getAllFoodByName(String account, String name) {
        String sql = "select * from d_food where s_food_name like '%" + name + "%' and s_business_id='" + account + "'";

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

    public static ArrayList<FoodBean> getAllFoodByFoodName(String name) {
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

    //根据商家id获取所有食物信息
    public static ArrayList<FoodBean> getAllFood(String business){

        Cursor res = db.rawQuery("select * from  d_food where s_business_id=?",new String[]{business});


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
}
