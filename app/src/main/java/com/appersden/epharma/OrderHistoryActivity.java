package com.appersden.epharma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class OrderHistoryActivity extends AppCompatActivity {

Spinner spinner;
LinearLayout ll_orderhistory1,ll_orderhistory2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        spinner=(Spinner)findViewById(R.id.spinnercomplete);
        ll_orderhistory1=(LinearLayout)findViewById(R.id.ll1order);
        ll_orderhistory2=(LinearLayout)findViewById(R.id.ll2order);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.complete_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        ll_orderhistory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CustomOrderHistoryDialog dialog=new CustomOrderHistoryDialog(OrderHistoryActivity.this);
                WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
                lWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT; // this is where the magic happens
                lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.show();
                // I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?
                dialog.getWindow().setAttributes(lWindowParams);
                dialog.getWindow().setGravity(Gravity.CENTER);
            }
        });

        ll_orderhistory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CustomOrderHistoryDialog dialog=new CustomOrderHistoryDialog(OrderHistoryActivity.this);
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
}
