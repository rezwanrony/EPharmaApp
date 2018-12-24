package com.appersden.epharma;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class CustomPrescriptionFullView extends Dialog {

    public Activity activity;
    Button btn_ok;
    ArrayList<Uri>uriArrayList;
    int pos;

    public CustomPrescriptionFullView(Activity activity, ArrayList<Uri>uriArrayList,int pos) {
        super(activity);
        this.activity = activity;
        this.uriArrayList=uriArrayList;
        this.pos=pos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fullprescriptionviewdialog);
        getWindow().setGravity(Gravity.CENTER);
        ImageView img_fullview=(ImageView)findViewById(R.id.img_previewprescription);
        Uri imageUri = uriArrayList.get(pos);
        img_fullview.setImageURI(imageUri);






    }
}
