package com.example.orderfood.borad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.example.orderfood.activity.RegisteredBusiness;
//监听wifi状态变化
public class WifiReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //获取动作action
        String action = intent.getAction();

        //监听wifi状态变化
        if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);
            //根据wifi的开关状态进行提示
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    Toast.makeText(context.getApplicationContext(), "wifi已关闭",Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    Toast.makeText(context.getApplicationContext(), "wifi已打开",Toast.LENGTH_SHORT).show();
                    break;
            }
        } //监听网络状态变化
        else if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null && networkInfo.isConnected()) {
                Toast.makeText(context.getApplicationContext(), "wifi已连接",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context.getApplicationContext(), "wifi未连接",Toast.LENGTH_SHORT).show();
            }
        }
    }
}