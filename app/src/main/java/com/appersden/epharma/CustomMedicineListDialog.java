package com.appersden.epharma;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;

import android.content.DialogInterface;

import android.content.Intent;

import android.database.Cursor;

import android.graphics.Bitmap;

import android.graphics.BitmapFactory;

import android.net.Uri;

import android.os.Bundle;

import android.app.Activity;

import android.os.Environment;

import android.provider.MediaStore;

import android.util.Log;

import android.view.Menu;

import android.view.View;

import android.widget.Button;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.OutputStream;

import static android.Manifest.permission.CAMERA;
import static android.app.Activity.RESULT_OK;

/**
 * Created by azeR on 12/11/2018.
 */

public class CustomMedicineListDialog extends Dialog {

    public Activity activity;
    Button btn_close;
    LinearLayout llchangepropic;
    Bitmap myBitmap;
    Uri picUri;
    String name;
    Spinner pices;
    Button btn_addtocart;


    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 107;
    public CustomMedicineListDialog(Activity activity,String name) {
        super(activity);
        this.activity = activity;
        this.name=name;
    }
    public CustomMedicineListDialog(Activity activity) {
        super(activity);
        this.activity = activity;
        this.name=name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.profilepopupdialog);
        getWindow().setGravity(Gravity.CENTER);
        btn_close=(Button) findViewById(R.id.btn_close_popup);
        TextView tv_proname=(TextView)findViewById(R.id.tv_medicinenamepopup);
        tv_proname.setText(name);
        btn_addtocart=(Button)findViewById(R.id.btn_addtocart);

        btn_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                CustomMedicineListDialog customMedicineListDialog=new CustomMedicineListDialog(activity);
                customMedicineListDialog.dismiss();
            }
        });

        pices=(Spinner)findViewById(R.id.spinnerunit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.pics_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
// Apply the adapter to the spinner
        pices.setAdapter(adapter);


        /*search=getIntent().getStringExtra("search");
        et_search.setQuery(search,true);*/
        /*Filter filter = customMedicineListAdapter.getFilter();
        filter.filter(search);*/


    }






}
