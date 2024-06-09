package com.example.orderfood.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.orderfood.R;
import com.example.orderfood.activity.UpdateGoodActivity;
import com.example.orderfood.adapter.FoodAdapter;
import com.example.orderfood.bean.FoodBean;
import com.example.orderfood.dao.FoodDao;
import com.example.orderfood.dao.UserDao;


import java.util.ArrayList;

public class BusinessActivityFragment extends Fragment {

    ArrayList<FoodBean> originalItems;

    ListView foodList_view;

    FoodAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_business, container, false);

        //实现加载食物列表
        foodList_view = view.findViewById(R.id.business_list_view);
        //获取登录的账号
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String account = sharedPreferences.getString("account","");

        ArrayList<FoodBean> food_list = FoodDao.getAllFood(account);//根据账号获取商家所有食物数据

        //为ListView设置Adapter
        if(food_list == null || food_list.size() == 0){
            foodList_view.setAdapter(null);
        }else{
            adapter = new FoodAdapter(getContext(),food_list);
            foodList_view.setAdapter(adapter);
        }


        //实现搜索功能
        EditText search = view.findViewById(R.id.business_search);//搜索的内容

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
                originalItems = FoodDao.getAllFoodByName(account,name);
                //将查询后的食物列表进行设置
                if(originalItems == null || originalItems.size() == 0){
                    foodList_view.setAdapter(null);
                }else{
                    adapter = new FoodAdapter(getActivity(),originalItems);
                    foodList_view.setAdapter(adapter);
                }
            }
        });

        Activity activity = getActivity();
        //注册上下文
        registerForContextMenu(foodList_view);
        //实现上下文菜单，长按弹出
        foodList_view.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                activity.openContextMenu(v);
                return true;
            }
        });
        return view;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //设置要弹出的菜单内容 包括标题和选项
        requireActivity().getMenuInflater().inflate(R.menu.business_home_menu,menu);
    }

    //设置点击的事件内容
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //获取上下文内容
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //获取点击的选项
        int position = info.position;
        switch (item.getItemId()){
            case R.id.business_home_list_menu_delete:
                deleteItem(position);
                return true;
            case R.id.business_home_list_menu_update:
                updateItem(position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    //执行删除，从界面上删除内容，同时也删除数据库内容
    private void deleteItem(int position){
        //实现在数据库中删除选中数据
        FoodBean temp = adapter.getItem(position);
        int result = FoodDao.delFood(temp.getS_food_id());
        if (result == 1){
            Toast.makeText(requireActivity(),"删除成功",Toast.LENGTH_SHORT).show();
            //从数据源中删除项
            adapter.remove(adapter.getItem(position));
            //通知适配器数据已修改
            adapter.notifyDataSetChanged();
        }else{
            Toast.makeText(requireActivity(),"删除失败",Toast.LENGTH_SHORT).show();
        }
    }

    //更改内容
    private void updateItem(int position){
        FoodBean temp = adapter.getItem(position);
        //跳转到更改界面
        Intent intent = new Intent(getActivity(), UpdateGoodActivity.class);
        //通过intent将食物id进行传值
        intent.putExtra("id",temp.getS_food_id());
        startActivity(intent);
    }
}