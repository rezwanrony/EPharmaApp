package com.appersden.epharma;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddressFragment extends Fragment {

    EditText et_address1, et_address2, et_address3;
    ImageView btn_okaddress1, btn_okaddress2, btn_okaddress3;
    RelativeLayout lladdresstwo,lladdressthree;
    ImageView img_editaddress1,img_editaddress2,img_editaddress3;
    ImageView btn_addanothertext;
    int clickcount=0;
    ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.address_fragment, container, false);
        et_address1=(EditText)rootView.findViewById(R.id.et_addressone);
        et_address2=(EditText)rootView.findViewById(R.id.et_addresstwomyprofile);
        et_address3=(EditText)rootView.findViewById(R.id.et_addressthreemyprofile);
        btn_okaddress1=(ImageView)rootView.findViewById(R.id.img_okaddressonemyprofile);
        btn_okaddress2=(ImageView)rootView.findViewById(R.id.img_okaddresstwomyprofile);
        btn_okaddress3=(ImageView)rootView.findViewById(R.id.img_okadddressthreemyprofile);
        lladdresstwo=(RelativeLayout)rootView.findViewById(R.id.lladdresstwo);
        lladdressthree=(RelativeLayout)rootView.findViewById(R.id.lladdressthree);
        img_editaddress1=(ImageView)rootView.findViewById(R.id.img_editaddressonemyprofile);
        img_editaddress2=(ImageView)rootView.findViewById(R.id.img_editaddresstwomyprofile);
        img_editaddress3=(ImageView)rootView.findViewById(R.id.img_editadressthreemyprofile);
        btn_addanothertext=(ImageView) rootView.findViewById(R.id.btn_addanotheraddressmyprofile);
        dialog=new ProgressDialog(getActivity());

        lladdresstwo.setVisibility(lladdresstwo.GONE);
        lladdressthree.setVisibility(lladdresstwo.GONE);
        img_editaddress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_address1.setEnabled(true);
                setAddress(et_address1.getText().toString());
            }
        });

        img_editaddress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_address2.setEnabled(true);
                setAddress(et_address2.getText().toString());
            }
        });

        img_editaddress3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_address3.setEnabled(true);
                setAddress(et_address3.getText().toString());
            }
        });

        btn_addanothertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()==true) {

                    clickcount = clickcount + 1;


                    if (clickcount == 1) {
                        lladdresstwo.setVisibility(lladdresstwo.VISIBLE);

                        //first time clicked to do this
                    } else if (clickcount == 2) {
                        if (et_address2.getText().toString().equals("")){

                            et_address2.setError("You cannot leave the field blank");
                            btn_addanothertext.setEnabled(false);
                            clickcount--;
                        }
                        else {

                            lladdressthree.setVisibility(lladdressthree.VISIBLE);
                            btn_addanothertext.setEnabled(true);
                        }


                    } else {
                        btn_addanothertext.setVisibility(btn_addanothertext.GONE);
                    }

                }
                else {
                    Toast.makeText(getActivity(),"You cannot leave these field blank",Toast.LENGTH_SHORT).show();
                }

            }
        });

        et_address1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_address1.setClickable(true);
                et_address1.setEnabled(true);
                btn_addanothertext.setEnabled(false);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_address1.setClickable(true);
                et_address1.setEnabled(true);
                btn_addanothertext.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                processaddressoneButtonByTextLength();
            }
        });


        et_address1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                // Get key action, up or down.
                int action = keyEvent.getAction();

                // Only process key up action, otherwise this listener will be triggered twice because of key down action.
                if(action == KeyEvent.ACTION_UP)
                {
                    processaddressoneButtonByTextLength();
                }
                return false;
            }
        });

        et_address2.setClickable(true);
        et_address2.setEnabled(true);
        et_address2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                btn_addanothertext.setEnabled(true);
                et_address2.setEnabled(true);
                btn_addanothertext.setEnabled(true);


            }

            @Override
            public void afterTextChanged(Editable editable) {
                processaddresstwoButtonByTextLength();
            }
        });

        et_address2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                // Get key action, up or down.
                int action = keyEvent.getAction();

                // Only process key up action, otherwise this listener will be triggered twice because of key down action.
                if(action == KeyEvent.ACTION_UP)
                {
                    processaddresstwoButtonByTextLength();
                }
                return false;
            }
        });

        et_address3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_addanothertext.setEnabled(true);
                et_address3.setEnabled(true);
                btn_addanothertext.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                processaddressthreeButtonByTextLength();
            }


        });
        return rootView;
    }

    private boolean validate(){
        if(et_address1.getText().toString().trim().equals("")) {
            et_address1.setError("You cannot leave the field blank");
            return false;
        }
            else {
            return true;
        }
}

    private void processaddressoneButtonByTextLength()
    {
        String inputText = et_address1.getText().toString();
        if(inputText.length() > 15)
        {
            btn_okaddress1.setEnabled(true);
            btn_okaddress1.setImageDrawable(getResources().getDrawable(R.drawable.tickgreen));
            btn_okaddress1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    et_address1.setEnabled(false);
                    btn_okaddress1.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    img_editaddress1.setEnabled(false);
                }
            });
            //MedicineListActivity.changeiconcolour(getActivity(),R.drawable.tick, btn_okaddress1);
        }
        else
        {
            btn_okaddress1.setEnabled(false);
            btn_okaddress1.setImageDrawable(getResources().getDrawable(R.drawable.tick));
        }
    }

    private void processaddresstwoButtonByTextLength()
    {
        String inputText = et_address2.getText().toString();
        if(inputText.length() > 15)
        {
            btn_okaddress2.setEnabled(true);
            btn_okaddress2.setImageDrawable(getResources().getDrawable(R.drawable.tickgreen));
            btn_okaddress2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    et_address2.setEnabled(false);
                    btn_okaddress2.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    img_editaddress2.setEnabled(false);
                }
            });
        }
        else
        {
            btn_okaddress2.setEnabled(false);
            btn_okaddress2.setImageDrawable(getResources().getDrawable(R.drawable.tick));
        }
    }

    private void processaddressthreeButtonByTextLength()
    {
        String inputText = et_address3.getText().toString();
        if(inputText.length() > 15)
        {
            btn_okaddress3.setEnabled(true);
            btn_okaddress3.setImageDrawable(getResources().getDrawable(R.drawable.tickgreen));
            btn_okaddress3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    et_address3.setEnabled(false);
                    btn_okaddress3.setImageDrawable(getResources().getDrawable(R.drawable.tick));
                    img_editaddress3.setEnabled(false);
                }
            });
        }
        else
        {
            btn_okaddress3.setEnabled(false);
            btn_okaddress3.setImageDrawable(getResources().getDrawable(R.drawable.tick));
        }
    }




    private void setAddress(final String address){
        dialog.setMessage("Please wait....");
        showDialog();
        String tag_string_req="Sign in";
        final String status="true";
        String id=SignUpActivity.getDefaults("id",getActivity());
        String url="http://173.82.105.191:7000/customer/insertAddress/"+id;
        Toast.makeText(getActivity(),address,Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(),id,Toast.LENGTH_SHORT).show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hideDialog();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(getActivity(),jsonObject.getString("msg"),Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                Toast.makeText(getActivity(),"Adding address failed",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map getParams() {
                // Posting parameters to main_page url
                Map params = new HashMap();
                params.put("address", address);
                params.put("status", status);
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
