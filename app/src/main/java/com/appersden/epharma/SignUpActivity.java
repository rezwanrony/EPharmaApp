package com.appersden.epharma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SignUpActivity extends AppCompatActivity {

    Spinner gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        gender=(Spinner)findViewById(R.id.spinnergender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
        gender.setAdapter(adapter);
    }
}
