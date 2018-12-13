package com.appersden.epharma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

public class MyProfileActivity extends AppCompatActivity {

    Spinner gender;
    ImageView img_search,img_home,img_cart,img_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        gender=(Spinner)findViewById(R.id.spinnergendermyprofile);
        img_home=(ImageView)findViewById(R.id.img_homesearchprofile);
        img_cart=(ImageView)findViewById(R.id.img_cartsearchprofile);
        img_profile=(ImageView)findViewById(R.id.img_profilesearchprofile);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
        gender.setAdapter(adapter);
        MedicineListActivity.changeiconcolourtogrey(MyProfileActivity.this,R.drawable.homepage,img_home);
        MedicineListActivity.changeiconcolourtogrey(MyProfileActivity.this,R.drawable.cart,img_cart);
        MedicineListActivity.changeiconcolour(MyProfileActivity.this,R.drawable.profile,img_profile);

    }
}
