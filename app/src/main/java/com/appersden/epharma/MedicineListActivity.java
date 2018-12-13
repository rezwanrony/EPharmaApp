package com.appersden.epharma;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MedicineListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    SearchView et_search;
    ArrayList<Medicine> medicineList;
    List searchlist;
    ListView lv_medicine;
    EmployeeAdapter customMedicineListAdapter;
    LinearLayout linearLayoutcard;
    String search;
    ImageView img_logo,img_background;
    CardView cardView;
    ActionBar actionBar;
    ImageView img_search,img_home,img_cart,img_profile;
    boolean doubleBackToExitPressedOnce = false;
    Toolbar toolbar;
    Button btn_back,btn_notification,btn_login,btn_signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicinelist);
        actionBar=getSupportActionBar();
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        btn_back=(Button)findViewById(R.id.toolbar_back);
        btn_notification=(Button)findViewById(R.id.toolbar_notification);

        img_home=(ImageView)findViewById(R.id.img_homesearch);
        img_cart=(ImageView)findViewById(R.id.img_cartsearch);
        img_profile=(ImageView)findViewById(R.id.img_profilesearch);
        btn_login=(Button)findViewById(R.id.toolbar_login);
        btn_signin=(Button)findViewById(R.id.toolbar_signin);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        et_search=(SearchView) findViewById(R.id.et_search);
        linearLayoutcard=(LinearLayout)findViewById(R.id.llcard);
        lv_medicine=(ListView)findViewById(R.id.lv_medicinelist);
        img_logo=(ImageView)findViewById(R.id.img_logo);
        img_background=(ImageView)findViewById(R.id.img_backgroundmedicinelist);
        cardView=(CardView)findViewById(R.id.cardview);
        //ImageView closeButton = (ImageView)mSearchView.findViewById(R.id.search_close_btn);
        medicineList=new ArrayList<Medicine>();
        searchlist=new ArrayList();

        btn_back.setVisibility(btn_back.GONE);
        btn_notification.setVisibility(btn_notification.GONE);
        lv_medicine.setVisibility(lv_medicine.GONE);
        btn_login.setVisibility(btn_notification.VISIBLE);
        btn_login.setTextSize(14);
        btn_signin.setTextSize(14);
        btn_signin.setVisibility(btn_signin.VISIBLE);
        clicklogin();

        cardView.setCardBackgroundColor(getResources().getColor(android.R.color.transparent));
        cardView.setCardElevation(0);
        cardView.setPadding(0,0,0,0);
        setupSearchView();

        medicineList.add(new Medicine("Napa Extra","Square","10$"));
        medicineList.add(new Medicine("Paracetamol","Square","10$"));
        medicineList.add(new Medicine("Tusca","Square","10$"));
        medicineList.add(new Medicine("Antacid","Square","10$"));
        medicineList.add(new Medicine("Ace","Square","10$"));
        medicineList.add(new Medicine("Brozedex","Square","10$"));
        medicineList.add(new Medicine("Flexo","Square","10$"));
        customMedicineListAdapter=new EmployeeAdapter(MedicineListActivity.this,medicineList);


        changeiconcolour(MedicineListActivity.this,R.drawable.homepage,img_home);
        changeiconcolourtogrey(MedicineListActivity.this,R.drawable.cart,img_cart);
        changeiconcolourtogrey(MedicineListActivity.this,R.drawable.profile,img_profile);

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicineListActivity.this,CartActivity.class));
            }
        });
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MedicineListActivity.this,MyProfileActivity.class));
            }
        });

        et_search.setOnSearchClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 lv_medicine.setVisibility(lv_medicine.GONE);
             }
         });


        lv_medicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MedicineListActivity.this,Main2Activity.class));
            }
        });


    }

    private void setupSearchView()
    {
        et_search.setOnQueryTextListener(this);

    }

    private void setupListview()
    {
        lv_medicine.setAdapter(customMedicineListAdapter);
        lv_medicine.setTextFilterEnabled(true);

    }

    @Override
    public boolean onQueryTextSubmit(String query)

    {
        Toast.makeText(this, "searchview is clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        btn_signin.setVisibility(btn_signin.GONE);
        btn_login.setVisibility(btn_login.GONE);
        btn_back.setVisibility(btn_notification.VISIBLE);
        btn_notification.setVisibility(btn_notification.VISIBLE);
        clickback();
        cardView.setCardBackgroundColor(getResources().getColor(android.R.color.white));
        cardView.setCardElevation(10);
        cardView.setPadding(0,10,0,0);
        img_background.setVisibility(img_background.GONE);
        lv_medicine.setVisibility(lv_medicine.VISIBLE);
        cardView.setVisibility(cardView.VISIBLE);
        setupListview();
        Filter filter = customMedicineListAdapter.getFilter();
        if (TextUtils.isEmpty(newText)) {
            filter.filter("");
        } else {

            filter.filter(newText);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        btn_back.setVisibility(btn_back.GONE);
        btn_notification.setVisibility(btn_notification.GONE);
        btn_signin.setVisibility(btn_signin.VISIBLE);
        btn_login.setVisibility(btn_login.VISIBLE);
        img_background.setVisibility(img_background.VISIBLE);
        changeiconcolour(MedicineListActivity.this,R.drawable.homepage,img_home);
        changeiconcolourtogrey(MedicineListActivity.this,R.drawable.cart,img_cart);
        changeiconcolourtogrey(MedicineListActivity.this,R.drawable.profile,img_profile);
        lv_medicine.setVisibility(lv_medicine.GONE);
        cardView.setCardBackgroundColor(getResources().getColor(android.R.color.transparent));
        cardView.setCardElevation(0);
        cardView.setPadding(0,0,0,0);
        clicklogin();
        et_search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
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
                return false;
            }
        });
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
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

    private  void clickback(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

    }

    public static void changeiconcolour(Context context, int resId, ImageView img){
        Drawable mDrawable = ContextCompat.getDrawable(context, resId);
        mDrawable.setColorFilter(Color.parseColor("#4DCBEC"), PorterDuff.Mode.MULTIPLY);
        img.setImageDrawable(mDrawable);
    }

    public static void changeiconcolourtogrey(Context context, int resId, ImageView img){
        Drawable mDrawable = ContextCompat.getDrawable(context, resId);
        mDrawable.setColorFilter(Color.parseColor("#D3D8E0"), PorterDuff.Mode.MULTIPLY);
        img.setImageDrawable(mDrawable);
    }



}
