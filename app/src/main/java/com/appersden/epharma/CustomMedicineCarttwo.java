package com.appersden.epharma;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class CustomMedicineCarttwo extends AppCompatActivity {
    public Activity activity;
    Button btn_close;
    LinearLayout llchangepropic;
    Bitmap myBitmap;
    Uri picUri;
    String name;
    Spinner pices;
    Button btn_edit,btn_close1,btnclose2;
    RelativeLayout ll1, ll2;
    Button btn_ordernow;

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 107;

    ImageView img_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.medicinecarttwo);
        getWindow().setGravity(Gravity.CENTER);
        btn_ordernow=(Button)findViewById(R.id.btn_ordernowcarttwo);
        img_order=(ImageView)findViewById(R.id.img_orderhistorylink);
        btn_ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final OrderPlaceDialog dialog=new OrderPlaceDialog(CustomMedicineCarttwo.this);
                WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
                lWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT; // this is where the magic happens
                lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                // I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
                dialog.getWindow().setAttributes(lWindowParams);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });

        img_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomMedicineCarttwo.this,OrderHistoryActivity.class));
            }
        });




    }
}
