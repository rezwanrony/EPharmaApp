package com.appersden.epharma;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicineListActivity extends AppCompatActivity {

    AutoCompleteTextView et_search;
    EditText upload;
    ArrayList medicineList;
    List searchlist;
    ListView lv_medicine;
    EmployeeAdapter customMedicineListAdapter;
    LinearLayout linearLayoutcard;
    String search;
    ImageView img_logo;
    ActionBar actionBar;
    DatabaseHandler db;
    ImageView img_search,img_home,img_cart,img_profile,img_profilepic;
    SessionManager session;
    boolean doubleBackToExitPressedOnce = false;
    Toolbar toolbar;
    TextView tv_logo;
    Button btn_back,btn_notification,btn_login,btn_signin,btn_logout;
    ArrayAdapter<String>medicineadapter;
    String gender;
    Button btn_logoutno;
    List<Uri> mSelected;
    private String TAG="MedicineListActivity";
    private static final int REQUEST_CODE_CHOOSE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicinelist);
        actionBar=getSupportActionBar();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        db=new DatabaseHandler(MedicineListActivity.this);
        tv_logo=(TextView)findViewById(R.id.tv_logo);
        session=new SessionManager(MedicineListActivity.this);
        db=new DatabaseHandler(MedicineListActivity.this);
        img_home=(ImageView)findViewById(R.id.img_homesearch);
        img_cart=(ImageView)findViewById(R.id.img_cartsearch);
        img_profile=(ImageView)findViewById(R.id.img_profilesearch);
        img_profilepic=(ImageView)findViewById(R.id.img_profilepicmedicinelist);
        btn_login=(Button)findViewById(R.id.toolbar_login);
        btn_signin=(Button)findViewById(R.id.toolbar_signin);
        btn_logout=(Button)findViewById(R.id.toolbar_logout);
        btn_logoutno=(Button)findViewById(R.id.btn_logoutno);
        upload=(EditText) findViewById(R.id.uploadprescription);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        et_search=(AutoCompleteTextView) findViewById(R.id.et_search);
        mSelected=new ArrayList<Uri>();
        networkcheck();
        //linearLayoutcard=(LinearLayout)findViewById(R.id.llcard);
        //ImageView closeButton = (ImageView)mSearchView.findViewById(R.id.search_close_btn);
        medicineList=new ArrayList();
        searchlist=new ArrayList();
        medicineList.add("Napa");
        medicineList.add("Napa Extra");
        medicineList.add("Napathin");
        medicineList.add("Napa Extend");
        medicineList.add("Napa plus");
        medicineList.add("Brozedex");
        medicineList.add("Flexo");
        //customMedicineListAdapter=new EmployeeAdapter(MedicineListActivity.this,medicineList);


        medicineadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,medicineList);
        et_search.setAdapter(medicineadapter);


        et_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final CustomMedicineListDialog dialog=new CustomMedicineListDialog(MedicineListActivity.this,medicineList.get(i).toString());
                WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
                lWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT; // this is where the magic happens
                lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                // I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
                dialog.getWindow().setAttributes(lWindowParams);
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialogdrawable));
                dialog.findViewById(R.id.btn_close_popup).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       dialog.dismiss();
                       et_search.setText("");
                    }
                });
            }
        });

        btn_logoutno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setLogin(false);
                startActivity(new Intent(MedicineListActivity.this,LoginActivity.class));
            }
        });




        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isStoragePermissionGranted()) {
                    Matisse.from(MedicineListActivity.this)
                            .choose(MimeType.ofAll())
                            .countable(true)
                            .maxSelectable(9)
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f)
                            .imageEngine(new GlideEngine())
                            .forResult(REQUEST_CODE_CHOOSE);
                }
                else {
                    Toast.makeText(getApplicationContext(),"You need to have storage permission",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (session.isLoggedIn()){
            btn_login.setVisibility(btn_login.GONE);
            btn_signin.setVisibility(btn_signin.GONE);
            btn_logout.setVisibility(btn_logout.VISIBLE);
            btn_logoutno.setVisibility(btn_logout.VISIBLE);
            btn_logout.setBackgroundResource(R.drawable.propicmale);
            clickpropic();
        }  else {

            btn_login.setVisibility(btn_login.VISIBLE);
            btn_signin.setVisibility(btn_signin.VISIBLE);
            btn_logout.setVisibility(btn_logout.GONE);
            btn_logoutno.setVisibility(btn_logout.GONE);
        }




        clicklogin();







        changeiconcolour(MedicineListActivity.this,R.drawable.homepage,img_home);
        changeiconcolourtogrey(MedicineListActivity.this,R.drawable.cart,img_cart);
        changeiconcolourtogrey(MedicineListActivity.this,R.drawable.profile,img_profile);

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MedicineListActivity.this,CustomCartDialog.class));
            }
        });
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (session.isLoggedIn()) {
                    startActivity(new Intent(MedicineListActivity.this, MyProfileActivity.class));
                }
                else {

                }
            }
        });


        et_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MedicineListActivity.this.medicineadapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.e("val", "requestCode ->  " + requestCode+"  resultCode "+resultCode);
        if(requestCode==REQUEST_CODE_CHOOSE&&resultCode==RESULT_OK) {
            mSelected=Matisse.obtainResult(data);
            startActivity(new Intent(MedicineListActivity.this,PrescriptionActivity.class).putParcelableArrayListExtra("uri", (ArrayList<? extends Parcelable>) mSelected));
            int selected=mSelected.size();
            //Toast.makeText(getApplicationContext(),mSelected.get(0).toString(),Toast.LENGTH_SHORT).show();
       /*     Toast.makeText(getApplicationContext(),String.valueOf(mSelected.size()),Toast.LENGTH_SHORT).show();
            Uri imageUri = Matisse.obtainResult(data).get(0);
            InputStream imageStream = null;
            String path=imageUri.getPath();
            File file=new File(path);
            Toast.makeText(getApplicationContext(),path,Toast.LENGTH_SHORT).show();*/
        }


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

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v(TAG,"Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }






    /*public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            btn_back.setVisibility(btn_back.GONE);
            btn_notification.setVisibility(btn_notification.GONE);
            btn_signin.setVisibility(btn_signin.VISIBLE);
            btn_login.setVisibility(btn_login.VISIBLE);
            img_background.setVisibility(img_background.VISIBLE);
            lv_medicine.setVisibility(lv_medicine.GONE);
            cardView.setCardBackgroundColor(getResources().getColor(android.R.color.transparent));
            cardView.setCardElevation(0);
            cardView.setPadding(0,0,0,0);
            setupSearchView();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    private  void clicklogin(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   startActivity(new Intent(MedicineListActivity.this,LoginActivity.class));
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicineListActivity.this,SignUpActivity.class));
            }
        });
    }

    private void clickpropic(){

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MedicineListActivity.this,MyProfileActivity.class));
            }
        });
    }




    public static void changeiconcolour(Context context, int resId, ImageView img){
        Drawable mDrawable = ContextCompat.getDrawable(context, resId);
        mDrawable.setColorFilter(Color.parseColor("#1abc9c"), PorterDuff.Mode.MULTIPLY);
        img.setImageDrawable(mDrawable);
    }

    public static void changeiconcolourtogrey(Context context, int resId, ImageView img){
        Drawable mDrawable = ContextCompat.getDrawable(context, resId);
        mDrawable.setColorFilter(Color.parseColor("#7f8c8d"), PorterDuff.Mode.MULTIPLY);
        img.setImageDrawable(mDrawable);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        networkcheck();
        if (session.isLoggedIn()){
            btn_login.setVisibility(btn_login.GONE);
            btn_signin.setVisibility(btn_signin.GONE);
            btn_logout.setVisibility(btn_logout.VISIBLE);
            btn_logoutno.setVisibility(btn_logout.VISIBLE);
            btn_logout.setBackgroundResource(R.drawable.propicmale);
            clickpropic();

        }  else {

            btn_login.setVisibility(btn_login.VISIBLE);
            btn_signin.setVisibility(btn_signin.VISIBLE);
            btn_logout.setVisibility(btn_logout.GONE);
            btn_logoutno.setVisibility(btn_logout.GONE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        networkcheck();
        if (session.isLoggedIn()){
            btn_login.setVisibility(btn_login.GONE);
            btn_signin.setVisibility(btn_signin.GONE);
            btn_logout.setVisibility(btn_logout.VISIBLE);
            btn_logoutno.setVisibility(btn_logout.VISIBLE);
            btn_logout.setBackgroundResource(R.drawable.propicmale);
            clickpropic();
        }  else {

            btn_login.setVisibility(btn_login.VISIBLE);
            btn_signin.setVisibility(btn_signin.VISIBLE);
            btn_logout.setVisibility(btn_logout.GONE);
            btn_logoutno.setVisibility(btn_logout.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        networkcheck();
        if (session.isLoggedIn()){
            btn_login.setVisibility(btn_login.GONE);
            btn_signin.setVisibility(btn_signin.GONE);
            btn_logout.setVisibility(btn_logout.VISIBLE);
            btn_logoutno.setVisibility(btn_logout.VISIBLE);
            btn_logout.setBackgroundResource(R.drawable.propicmale);
            clickpropic();
        }  else {

            btn_login.setVisibility(btn_login.VISIBLE);
            btn_signin.setVisibility(btn_signin.VISIBLE);
            btn_logout.setVisibility(btn_logout.GONE);
            btn_logoutno.setVisibility(btn_logout.GONE);
            clickpropic();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkcheck();
        if (session.isLoggedIn()){
            btn_login.setVisibility(btn_login.GONE);
            btn_signin.setVisibility(btn_signin.GONE);
            btn_logout.setVisibility(btn_logout.VISIBLE);
            btn_logoutno.setVisibility(btn_logout.VISIBLE);
            btn_logout.setBackgroundResource(R.drawable.propicmale);
            clickpropic();
        }  else {

            btn_login.setVisibility(btn_login.VISIBLE);
            btn_signin.setVisibility(btn_signin.VISIBLE);
            btn_logout.setVisibility(btn_logout.GONE);
            btn_logoutno.setVisibility(btn_logout.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void networkcheck(){
        if (isNetworkAvailable()){

        }
        else {

            try {
                AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();

                alertDialog.setTitle("Info");
                alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();

                    }
                });

                alertDialog.show();
            } catch (Exception e) {
                Log.d("Dialog", "Show Dialog: " + e.getMessage());
            }
        }

    }
}
