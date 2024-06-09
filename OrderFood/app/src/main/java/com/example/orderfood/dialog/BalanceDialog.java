package com.example.orderfood.dialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.activity.BuyFoodActivity;

public class BalanceDialog extends Dialog {

    public enum ButtonID{BUTTON_NONE,BUTTON_CANCLE,BUTTON_PAY};
    public ButtonID mBtnClicked = ButtonID.BUTTON_NONE;

    public BalanceDialog(Context context) {
        super(context);
        setContentView(R.layout.balance_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.balance_dialog);
        this.setTitle("支付订单");
        setCancelable(true);
        Button pay_btn = findViewById(R.id.balance_pay_btn);
        Button cancle_btn = findViewById(R.id.balance_cancle_btn);
        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.balance_pay_btn:
                        mBtnClicked = ButtonID.BUTTON_PAY;
                        break;
                    case R.id.balance_cancle_btn:
                        mBtnClicked = ButtonID.BUTTON_CANCLE;
                        break;

                }
                dismiss();
            }
        };
        pay_btn.setOnClickListener(buttonListener);
        cancle_btn.setOnClickListener(buttonListener);

    }
}