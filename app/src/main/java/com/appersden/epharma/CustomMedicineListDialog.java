package com.appersden.epharma;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by azeR on 12/11/2018.
 */

public class CustomMedicineListDialog extends Dialog implements SearchView.OnQueryTextListener{

    public Activity activity;
    SearchView et_search;
    ArrayList<Medicine> medicineList;
    List searchlist;
    ListView lv_medicine;
    EmployeeAdapter customMedicineListAdapter;
    LinearLayout linearLayoutcard;
    String search;
    public CustomMedicineListDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_medicinelist);
        getWindow().setLayout(WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        et_search=(SearchView) findViewById(R.id.et_search);
        linearLayoutcard=(LinearLayout)findViewById(R.id.llcard);
        lv_medicine=(ListView)findViewById(R.id.lv_medicinelist);
        medicineList=new ArrayList<Medicine>();
        searchlist=new ArrayList();
        medicineList.add(new Medicine("Napa Extra","Square","10$"));
        medicineList.add(new Medicine("Paracetamol","Square","10$"));
        medicineList.add(new Medicine("Tusca","Square","10$"));
        medicineList.add(new Medicine("Antacid","Square","10$"));
        medicineList.add(new Medicine("Ace","Square","10$"));
        medicineList.add(new Medicine("Brozedex","Square","10$"));
        medicineList.add(new Medicine("Flexo","Square","10$"));
        customMedicineListAdapter=new EmployeeAdapter(activity,medicineList);
        lv_medicine.setAdapter(customMedicineListAdapter);

        /*search=getIntent().getStringExtra("search");
        et_search.setQuery(search,true);*/
        /*Filter filter = customMedicineListAdapter.getFilter();
        filter.filter(search);*/
        lv_medicine.setTextFilterEnabled(true);


        setupSearchView();


        et_search.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_medicine.setVisibility(lv_medicine.GONE);
            }
        });

        lv_medicine.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               activity.startActivity(new Intent(activity,Main2Activity.class));
            }
        });


    }

    private void setupSearchView()
    {
        et_search.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String query)

    {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Filter filter = customMedicineListAdapter.getFilter();
        if (TextUtils.isEmpty(newText)) {
            filter.filter("");
        } else {

            filter.filter(newText);
        }
        return true;
    }
}
