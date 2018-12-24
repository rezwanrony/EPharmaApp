package com.appersden.epharma;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class CustomOrderHistoryDialog extends Dialog {
    public Activity activity;
    Button btn_ok;

    public CustomOrderHistoryDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_orderhistorydialog);
        getWindow().setGravity(Gravity.CENTER);
        btn_ok=(Button)findViewById(R.id.btn_okorderhistory);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                CustomOrderHistoryDialog dialog=new CustomOrderHistoryDialog(activity);
                dialog.dismiss();
            }
        });





    }
}
