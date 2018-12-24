package com.appersden.epharma;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionActivity extends AppCompatActivity {

    List<Uri> mSelected;
    private static final int REQUEST_CODE_CHOOSE=100;
    ListView lv_prescription;
    Toolbar toolbar;
    ActionBar actionBar;
    ArrayList<Uri>uriArrayList;
    Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        uriArrayList=getIntent().getParcelableArrayListExtra("uri");
        Toast.makeText(getApplicationContext(),uriArrayList.get(0).toString(),Toast.LENGTH_SHORT).show();
        actionBar=getSupportActionBar();
        toolbar=(Toolbar)findViewById(R.id.toolbarprescription);
        btn_next=(Button)findViewById(R.id.btn_next);
        lv_prescription=(ListView)findViewById(R.id.listviewprescription);
        CustomPrescriptionListAdapter customPrescriptionListAdapter=new CustomPrescriptionListAdapter(PrescriptionActivity.this,uriArrayList);
        lv_prescription.setAdapter(customPrescriptionListAdapter);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrescriptionActivity.this,CustomMedicineCarttwo.class));
            }
        });
        lv_prescription.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final CustomPrescriptionFullView dialog=new CustomPrescriptionFullView(PrescriptionActivity.this,uriArrayList,i);
                WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
                lWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT; // this is where the magic happens
                lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                // I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
                dialog.getWindow().setAttributes(lWindowParams);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });
    }




    // other 'case' lines to check for other
    // permissions this app might request.


    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }



}
