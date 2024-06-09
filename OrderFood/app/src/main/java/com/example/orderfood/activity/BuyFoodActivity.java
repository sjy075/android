package com.example.orderfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.MainActivity;
import com.example.orderfood.R;
import com.example.orderfood.adapter.BuyFoodAdapter;
import com.example.orderfood.adapter.FoodAdapter;
import com.example.orderfood.bean.BusinessBean;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.dao.UserDao;
import com.example.orderfood.dialog.BalanceDialog;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class BuyFoodActivity extends AppCompatActivity {
    ArrayList<FoodBean> originalItems;

    BuyFoodAdapter adapter;

    ArrayList<FoodBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_food);

        ImageView back = findViewById(R.id.buy_food_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BuyFoodActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //加载食物信息
        ListView listView = findViewById(R.id.buy_food_list);
        list = FoodDao.getAllFood();
        if(list != null && list.size() > 0){
            adapter = new BuyFoodAdapter(this,list);
            listView.setAdapter(adapter);
        }else{
            listView.setAdapter(null);
        }
        TextView total_view = findViewById(R.id.user_food_list_total);
        Button js_btn = findViewById(R.id.user_buy_food_js_btn);
        js_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double total = Double.parseDouble(total_view.getText().toString());
                if(total == 0){
                    Toast.makeText(BuyFoodActivity.this,"未选择商品无法结算",Toast.LENGTH_SHORT).show();
                }else{
                    BalanceDialog balanceDialog = new BalanceDialog(BuyFoodActivity.this);
                    balanceDialog.show();
                    balanceDialog.setOnDismissListener(dialog -> {
                        switch (balanceDialog.mBtnClicked){
                            case BUTTON_PAY:
                                Toast.makeText(BuyFoodActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(BuyFoodActivity.this,BuyFoodActivity.class);
                                startActivity(intent);
                                break;
                            case BUTTON_CANCLE:
                                balanceDialog.dismiss();
                        }

                    });

                }
            }
        });

        //搜索框实现
        EditText search = findViewById(R.id.business_search);//搜索的内容

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //根据食物名称模糊查询所有商家的食物
                String name = search.getText().toString();
                originalItems = FoodDao.getAllFoodByFoodName(name);
                //将查询后的食物列表进行设置
                if(originalItems == null || originalItems.size() == 0){
                    listView.setAdapter(null);
                }else{
                    adapter = new BuyFoodAdapter(BuyFoodActivity.this,originalItems);
                    listView.setAdapter(adapter);
                }
            }
        });


    }
}