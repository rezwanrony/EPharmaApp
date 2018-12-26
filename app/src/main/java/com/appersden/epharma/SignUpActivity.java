package com.appersden.epharma;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
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

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    Spinner gender;
    EditText et_username,et_phone,et_password,et_confpass,et_email,et_name;
    Button btn_signup;
    DatabaseHandler db;
    TextView tv_linklogin;
    Button btn_login;
    String username="",password="",confpass="",gendertext="",phonenumber="",email="";
    Context activity;
    SessionManager session;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        activity=SignUpActivity.this;
        gender=(Spinner)findViewById(R.id.spinnergender);
        et_name=(EditText)findViewById(R.id.et_namesignup);
        et_username=(EditText)findViewById(R.id.et_usernamesignup);
        et_phone=(EditText)findViewById(R.id.et_phonesignup);
        et_password=(EditText)findViewById(R.id.et_passwordsignup);
        et_confpass=(EditText)findViewById(R.id.et_confpasssignup);
        et_email=(EditText)findViewById(R.id.et_emailsignup);
        dialog=new ProgressDialog(SignUpActivity.this);
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

        networkcheck();
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
                String name=et_name.getText().toString();
                String usernames=et_username.getText().toString();
                String emails=et_email.getText().toString();
                String phones=et_phone.getText().toString();
                String passwords=et_password.getText().toString();
                String image="";
                if (validate()==true) {
                    if (et_password.getText().toString().equals(et_confpass.getText().toString())) {
                        db.addUser(usernames,passwords,phones,emails,gendertext);
                        getSignup(name,usernames,phones,emails,gendertext,passwords,image);

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


  /*  private void getSignedUp(String fullname,String username,String phone, String email,String gender,String password, String image){


        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(ApiUtils.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface=retrofit.create(ApiInterface.class);

        Call<RegisterResponse> call=apiInterface.userRegistration(fullname,username,phone,email,gender,password,image);

        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {


                        Toast.makeText(getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, MedicineListActivity.class));
                        session.setLogin(true);
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Sign up failed!!!The fields needs to be unique and valid",Toast.LENGTH_SHORT).show();
            }
        });

    }*/
    public static void setDefaults(String key, String value,  Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }


    private void getSignup(final String fullname, final String username, final String phone, final String email, final String gender, final String password, final String image){

        dialog.setMessage("Signing up....");
        showDialog();
        String tag_string_req="Sign in";
        String url="http://173.82.105.191:7000/customer/register";
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String token=jsonObject.getString("data");
                    setDefaults("token", token, SignUpActivity.this);
                    Toast.makeText(getApplicationContext(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();
                    getSignIn(username,password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(getApplicationContext(),"Sign up failed!!Check your internet connection",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to main_page url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", fullname);
                params.put("username", username);
                params.put("phone", phone);
                params.put("email", email);
                params.put("gender", gender);
                params.put("password", password);
                params.put("image", image);
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
                    startActivity(new Intent(SignUpActivity.this, MedicineListActivity.class));
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

}
