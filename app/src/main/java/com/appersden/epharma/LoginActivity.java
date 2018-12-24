package com.appersden.epharma;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity{

    SessionManager session;
    EditText et_username,et_password;
    Button btn_login;
    DatabaseHandler db;
    TextView tv_linksignup;
    Context activity;
    String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity=LoginActivity.this;
        et_username=(EditText)findViewById(R.id.et_usernamelogin);
        et_password=(EditText)findViewById(R.id.et_passwordlogin);
        btn_login=(Button)findViewById(R.id.btn_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        db=new DatabaseHandler(LoginActivity.this);
        session=new SessionManager(LoginActivity.this);
        tv_linksignup=(TextView)findViewById(R.id.link_signup);
        Map map=new HashMap();
        map=db.getUserDetails();
        if (db.getUserDetails().size()!=0) {
            username = map.get("username").toString();
            password = map.get("password").toString();
        }
        else {

            username="";
            password="";
        }
        /*Toast.makeText(getApplicationContext(),username,Toast.LENGTH_SHORT).show();*/

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (validate()==true) {

                    if (et_username.getText().toString().equals(username)&&et_password.getText().toString().equals(password)) {
                        session.setLogin(true);
                        startActivity(new Intent(LoginActivity.this,MedicineListActivity.class));
                        Toast.makeText(activity, "Successfully Logged in", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(activity,"Invalid username or password",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(activity,"You have to fill up all the required fields",Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_linksignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
            }
        });
    }

    private boolean validate(){
        if(et_username.getText().toString().trim().equals("")) {
            return false;
        }
        else if(et_password.getText().toString().trim().equals("")) {
            return false;
        }
        else
            return true;
    }


}
