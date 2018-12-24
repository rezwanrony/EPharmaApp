package com.appersden.epharma;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    Spinner gender;
    EditText et_username,et_phone,et_password,et_confpass,et_email;
    Button btn_signup;
    DatabaseHandler db;
    TextView tv_linklogin;
    Button btn_login;
    String username="",password="",confpass="",gendertext="",phonenumber="",email="";
    Context activity;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        activity=SignUpActivity.this;
        gender=(Spinner)findViewById(R.id.spinnergender);
        et_username=(EditText)findViewById(R.id.et_usernamesignup);
        et_phone=(EditText)findViewById(R.id.et_phonesignup);
        et_password=(EditText)findViewById(R.id.et_passwordsignup);
        et_confpass=(EditText)findViewById(R.id.et_confpasssignup);
        et_email=(EditText)findViewById(R.id.et_emailsignup);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        btn_signup=(Button)findViewById(R.id.btn_signup);
        btn_login=(Button)findViewById(R.id.toolbar_loginsignup);
        session=new SessionManager(SignUpActivity.this);
        username=et_username.getText().toString();
        phonenumber=et_phone.getText().toString();
        password=et_password.getText().toString();
        confpass=et_confpass.getText().toString();
        email=et_email.getText().toString();
        db=new DatabaseHandler(activity);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

      /*  ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner*/


        List<String> flowers = new ArrayList<>();
        flowers.add("Male");
        flowers.add("Female");
        flowers.add("Others");

        // Initialize an array adapter
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(activity,R.layout.support_simple_spinner_dropdown_item,flowers) {
            public View getView(int position, View convertView, ViewGroup parent) {
                // Cast the spinner collapsed item (non-popup item) as a text view
                TextView tv = (TextView) super.getView(position, convertView, parent);

                // Set the text color of spinner item
                tv.setTextColor(Color.BLACK);

                // Return the view
                return tv;
            }
        };

        gender.setAdapter(mAdapter);


        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){

                    gendertext="Male";
                }
                else if (i==1){

                    gendertext="Female";

                }
                else if (i==2){

                    gendertext="Others";

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                gendertext="Male";
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteUsers();
                String usernames=et_username.getText().toString();
                String emails=et_email.getText().toString();
                String phones=et_phone.getText().toString();
                String passwords=et_password.getText().toString();
                if (validate()==true) {
                    if (et_password.getText().toString().equals(et_confpass.getText().toString())) {
                        db.addUser(usernames,passwords,phones,emails,gendertext);
                        startActivity(new Intent(activity,MedicineListActivity.class));
                        session.setLogin(true);
                        Toast.makeText(activity, "Successfully Signed up", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(activity, "Password don't match", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(activity, "You have to fill up all the required fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validate(){
        boolean valid = true;

        String email = et_username.getText().toString();
        String phone = et_phone.getText().toString();
        String password = et_password.getText().toString();
        String confpass = et_confpass.getText().toString();

        if (email.isEmpty()) {
            //Toast.makeText(getApplicationContext(),"username should contain more than 6 characters",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (phone.isEmpty()) {
            //Toast.makeText(getApplicationContext(),"Phone Number must contain 11 numbers",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (password.isEmpty()) {
            //Toast.makeText(getApplicationContext(),"between 4 and 10 alphanumeric characters",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if (confpass.isEmpty()) {
            //Toast.makeText(getApplicationContext(),"You cannot leave the field blank",Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }
}
