package com.appersden.epharma;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    SearchView et_search;
    ImageView img_search,img_home,img_cart,img_profile;
    LinearLayout lay_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        et_search=(SearchView) findViewById(R.id.et_searchmedicine);
        //img_search=(ImageView)findViewById(R.id.img_search);
        img_home=(ImageView)findViewById(R.id.img_homesearch);
        img_cart=(ImageView)findViewById(R.id.img_cartsearch);
        img_profile=(ImageView)findViewById(R.id.img_profilesearch);
        lay_search=(LinearLayout)findViewById(R.id.llsearch);


        changeiconcolour(SearchActivity.this,R.drawable.homepage,img_home);
        changeiconcolourtogrey(SearchActivity.this,R.drawable.cart,img_cart);
        changeiconcolourtogrey(SearchActivity.this,R.drawable.profile,img_profile);

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this,CartActivity.class));
            }
        });

        et_search.setOnQueryTextListener(this);
    }

    public static void changeiconcolour(Context context, int resId, ImageView img){
        Drawable mDrawable = ContextCompat.getDrawable(context, resId);
        mDrawable.setColorFilter(Color.parseColor("#FF86A7"), PorterDuff.Mode.MULTIPLY);
        img.setImageDrawable(mDrawable);
    }

    public static void changeiconcolourtogrey(Context context, int resId, ImageView img){
        Drawable mDrawable = ContextCompat.getDrawable(context, resId);
        mDrawable.setColorFilter(Color.parseColor("#D3D8E0"), PorterDuff.Mode.MULTIPLY);
        img.setImageDrawable(mDrawable);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //dialog.getWindow().setGravity(Gravity.BOTTOM);
        return false;
    }
}
