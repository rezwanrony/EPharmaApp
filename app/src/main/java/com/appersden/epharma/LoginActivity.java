package com.appersden.epharma;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity{

    SessionManager session;
    EditText et_username,et_password;
    Button btn_login;
    DatabaseHandler db;
    TextView tv_linksignup;
    Context activity;
    String username,password;
    ApiInterface apiInterface;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity=LoginActivity.this;
        apiInterface= ApiUtils.getAPIService();
        et_username=(EditText)findViewById(R.id.et_usernamelogin);
        et_password=(EditText)findViewById(R.id.et_passwordlogin);
        btn_login=(Button)findViewById(R.id.btn_login);
        dialog=new ProgressDialog(LoginActivity.this);
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
                    String username=et_username.getText().toString();
                    String password=et_password.getText().toString();
                        getSignIn(username,password);

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

    private void getLoggedIn(String username,String password){


        apiInterface.checkuser(username,password).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.code()==200) {
                    if (response.isSuccessful()) {
                        String message = response.body().getMsg();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        Log.d("Login", "loginresponse" + response.body().toString());

                    }

                    else {
                        Toast.makeText(getApplicationContext(), "response is not successfull", Toast.LENGTH_SHORT).show();

                    }

                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Invalid username or password",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getSignIn(final String username, final String password){
        dialog.setMessage("Signing in...");
        showDialog();
        String tag_string_req="Sign in";
        String url="http://173.82.105.191:7000/customer/authentication";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                    SignUpActivity.setDefaults("token", jsonObject.getString("data"), LoginActivity.this);
                    startActivity(new Intent(LoginActivity.this, MedicineListActivity.class));
                    session.setLogin(true);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(getApplicationContext(),"Sign in failed!!Check your internet connection",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to main_page url
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;

            }

        };
        Log.v("_______", "+++++++" + stringRequest);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }


    private void showDialog() {
        if (!dialog.isShowing())
            dialog.show();
    }

    private void hideDialog() {
        if (dialog.isShowing())
            dialog.dismiss();
    }


}


