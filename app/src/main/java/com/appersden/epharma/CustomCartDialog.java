package com.appersden.epharma;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomCartDialog extends AppCompatActivity {

    Context activity;
    Button btn_close;
    LinearLayout llchangepropic;
    Bitmap myBitmap;
    Uri picUri;
    String name;
    Spinner pices;
    Button btn_edit,btn_close1,btnclose2;
    RelativeLayout ll1, ll2;
    Button btn_ordernow;
    LinearLayout expandablelayout,expandablelayout2;
    Button btn_addtocart;

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 107;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicinecartone);
        activity=CustomCartDialog.this;
        btn_edit=(Button)findViewById(R.id.btn_editmedicinecartone);
        btn_close1=(Button)findViewById(R.id.btn_close_popupcart);
        btnclose2=(Button)findViewById(R.id.btn_close_popups);
        ll1=(RelativeLayout)findViewById(R.id.rlllone);
        ll2=(RelativeLayout)findViewById(R.id.rllltwo);
        btn_ordernow=(Button)findViewById(R.id.btn_ordernowcartone);
        expandablelayout=(LinearLayout)findViewById(R.id.expanded);
        expandablelayout2=(LinearLayout)findViewById(R.id.expandchild);
        expandablelayout.setVisibility(expandablelayout.GONE);
        expandablelayout2.setVisibility(expandablelayout.VISIBLE);


        btn_ordernow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomCartDialog.this,CustomMedicineCarttwo.class));
            }
        });

        btn_close1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll1.setVisibility(ll1.GONE);
            }

        });

        btnclose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_edit.setBackgroundResource(R.drawable.tick);
                expandablelayout.setVisibility(expandablelayout.VISIBLE);
                expandablelayout2.setVisibility(expandablelayout.VISIBLE);
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_edit.setBackgroundResource(R.drawable.pencil);
                        expandablelayout.setVisibility(expandablelayout.GONE);
                    }
                });
            }
        });

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_edit.setBackgroundResource(R.drawable.tick);
                expandablelayout.setVisibility(expandablelayout.VISIBLE);
                expandablelayout2.setVisibility(expandablelayout.VISIBLE);
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_edit.setBackgroundResource(R.drawable.pencil);
                        expandablelayout.setVisibility(expandablelayout.GONE);
                    }
                });
            }
        });


        /*search=getIntent().getStringExtra("search");
        et_search.setQuery(search,true);*/
        /*Filter filter = customMedicineListAdapter.getFilter();
        filter.filter(search);*/


    }



}
