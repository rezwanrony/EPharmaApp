package com.appersden.epharma;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class OrderPlaceDialog extends Dialog {
    public Activity activity;
    Button btn_close;
    LinearLayout llchangepropic;
    Bitmap myBitmap;
    Uri picUri;
    String name;
    Spinner pices;
    Button btn_edit,btn_close1,btnclose2;
    RelativeLayout ll1, ll2;
    Button ok;


    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 107;

    public OrderPlaceDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.orderplacelayout);
        getWindow().setGravity(Gravity.BOTTOM);

        ok=(Button)findViewById(R.id.btn_okorderplace);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 OrderPlaceDialog dialog=new OrderPlaceDialog(activity);
                 dialog.dismiss();
                 activity.startActivity(new Intent(activity,MedicineListActivity.class));
            }
        });
        /*search=getIntent().getStringExtra("search");
        et_search.setQuery(search,true);*/
        /*Filter filter = customMedicineListAdapter.getFilter();
        filter.filter(search);*/


    }
}
