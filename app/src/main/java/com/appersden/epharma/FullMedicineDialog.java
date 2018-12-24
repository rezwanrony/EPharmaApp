package com.appersden.epharma;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

public class FullMedicineDialog extends Dialog {

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

    public FullMedicineDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fullmedicineviewdialog);
        getWindow().setGravity(Gravity.CENTER);

        ok=(Button)findViewById(R.id.btn_tickmedicinecartones);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                FullMedicineDialog customCartDialog=new FullMedicineDialog(activity);
                customCartDialog.dismiss();
                final OrderPlaceDialog dialog=new OrderPlaceDialog(activity);
                WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
                lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT; // this is where the magic happens
                lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                // I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
                dialog.getWindow().setAttributes(lWindowParams);
                dialog.getWindow().setGravity(Gravity.TOP);
                dialog.getWindow().setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.customdrawablecart));
            }
        });
        /*search=getIntent().getStringExtra("search");
        et_search.setQuery(search,true);*/
        /*Filter filter = customMedicineListAdapter.getFilter();
        filter.filter(search);*/


    }
}
