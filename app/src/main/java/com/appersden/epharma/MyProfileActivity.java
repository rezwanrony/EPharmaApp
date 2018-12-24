package com.appersden.epharma;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends AppCompatActivity {

    Button btn_back;
    private static final int PHOTO_REQUEST = 1;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    private static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    Uri photoURI;
    File file;
    InputStream ims;
    Uri imageUri;
    String mCurrentPhotoPath;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    Spinner gender;
    int clickcount = 0;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    byte[] byteArray;
    String path;
    Bitmap b, cropimage;
    ImageView img_search, img_home, img_cart, img_profile;
    CircleImageView img_profilepic;
    EditText et_username, et_phone, et_address1, et_address2, et_address3, et_oldpassword, et_newpassword, et_retypepassword;
    Button btn_okusername, btn_okphone, btn_okgender, btn_okaddress1, btn_okaddress2, btn_okaddress3, btn_addanothertext, btn_okchangepass;
    ImageView img_editusername, img_editphone, img_editaddresss1, img_editaddresss2, img_editgender;
    LinearLayout lladdresstwo, lladdressthree;
    String TAG = "permission";
    private int RESULT_LOAD_IMG = 1;
    SessionManager session;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    ImageView img_propic,img_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        img_propic=(ImageView)findViewById(R.id.img_profilepicmyprofile);
        img_logout=(ImageView)findViewById(R.id.img_logoutmyprofile);
        RadioGroup radiogroup=(RadioGroup)findViewById(R.id.rdogrp);
        final RadioButton radioButton1=(RadioButton)findViewById(R.id.radiobtn1);
        final RadioButton radioButton2=(RadioButton)findViewById(R.id.radiobtn2);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new DetailsFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (radioButton1.isChecked()){
                    radioButton1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.fragmentcolor, null));
                    radioButton2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new DetailsFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }

                if (radioButton2.isChecked()){
                    radioButton2.setTextColor(ResourcesCompat.getColor(getResources(), R.color.fragmentcolor, null));
                    radioButton1.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new AddressFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });
    /*    img_home=(ImageView)findViewById(R.id.img_homesearchprofile);
        img_cart=(ImageView)findViewById(R.id.img_cartsearchprofile);
        img_profile=(ImageView)findViewById(R.id.img_profilesearchprofile);
        img_profilepic=(CircleImageView) findViewById(R.id.img_profilepic);
        et_username=(EditText)findViewById(R.id.et_usernamemyprofile);
        lladdresstwo=(LinearLayout)findViewById(R.id.lladdresstwo);
        lladdressthree=(LinearLayout)findViewById(R.id.lladdressthree);
        et_phone=(EditText)findViewById(R.id.et_phonemyprofile);
        session=new SessionManager(MyProfileActivity.this);
        et_address1=(EditText)findViewById(R.id.et_addressone);
        et_address2=(EditText)findViewById(R.id.et_addresstwomyprofile);
        et_address3=(EditText)findViewById(R.id.et_addressthreemyprofile);
        et_oldpassword=(EditText)findViewById(R.id.et_oldpasswordprofile);
        et_newpassword=(EditText)findViewById(R.id.et_newpassprofile);
        et_retypepassword=(EditText)findViewById(R.id.et_confpassprofile);
        btn_okusername=(Button)findViewById(R.id.btn_okusernamemyprofile);
        btn_okphone=(Button)findViewById(R.id.btn_okphonemyprofile);
        btn_okgender=(Button)findViewById(R.id.btn_okgendermyprofile);
        btn_okaddress1=(Button)findViewById(R.id.btn_addressonemyprofile);
        btn_okaddress2=(Button)findViewById(R.id.btn_addresstwomyprofile);
        btn_okaddress3=(Button)findViewById(R.id.btn_addressthreemyprofile);
        btn_addanothertext=(Button)findViewById(R.id.btn_addanotheraddressmyprofile);
        btn_okchangepass=(Button)findViewById(R.id.btn_okchangepass);
        img_editusername=(ImageView)findViewById(R.id.img_editusernamemyprofile);
        img_editphone=(ImageView)findViewById(R.id.img_editphonemyprofile);
        img_editgender=(ImageView)findViewById(R.id.img_editgendermyprofile);
        db=new SQLiteHandler(MyProfileActivity.this);

        btn_okusername.setVisibility(btn_okusername.GONE);
        btn_okphone.setVisibility(btn_okusername.GONE);
        btn_okgender.setVisibility(btn_okusername.GONE);
        btn_okaddress1.setVisibility(btn_okusername.GONE);
        btn_okaddress2.setVisibility(btn_okusername.GONE);
        btn_okaddress3.setVisibility(btn_okusername.GONE);
        btn_okchangepass.setVisibility(btn_okusername.GONE);
        Map maps=new HashMap();
        maps = db.getUserDetails();
        final String gendertexts = maps.get("gender").toString();
        if (gendertexts.equals("Female")){
            img_profilepic.setImageDrawable(getResources().getDrawable(R.drawable.propicfemale));
        }

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyProfileActivity.this,MedicineListActivity.class));
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
// Apply the adapter to the spinner
        gender.setAdapter(adapter);
        gender.setEnabled(false);



        if (session.isLoggedIn()) {
            Map map=new HashMap();
            map = db.getUserDetails();
            String username = map.get("username").toString();
            String phone = map.get("phone").toString();
            final String gendertext = map.get("gender").toString();
            et_username.setText(username);
            et_phone.setText(phone);
            if (gendertext.equals("Female")){
                ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this,
                        R.array.genderfemale_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
// Apply the adapter to the spinner
                gender.setAdapter(adapters);
            }
        }

        MedicineListActivity.changeiconcolourtogrey(MyProfileActivity.this,R.drawable.homepage,img_home);
        MedicineListActivity.changeiconcolourtogrey(MyProfileActivity.this,R.drawable.cart,img_cart);
        MedicineListActivity.changeiconcolour(MyProfileActivity.this,R.drawable.profile,img_profile);

        img_editusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_username.setEnabled(true);
                et_username.setClickable(true);
            }
        });



        img_editphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_phone.setEnabled(true);
            }
        });

        img_editgender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender.setEnabled(true);
            }
        });

        img_profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        lladdresstwo.setVisibility(lladdresstwo.GONE);
        lladdressthree.setVisibility(lladdressthree.GONE);

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
                    Toast.makeText(getApplicationContext(),"You cannot leave these field blank",Toast.LENGTH_SHORT).show();
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
            }

            @Override
            public void afterTextChanged(Editable editable) {
                processaddresstwoButtonByTextLength();
            }
        });


        et_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_username.setClickable(true);
                et_username.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_username.setClickable(true);
                et_username.setEnabled(true);
                btn_okusername.setVisibility(btn_okusername.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable editable) {
                processusernameButtonByTextLength();
            }
        });


        et_username.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                // Get key action, up or down.
                int action = keyEvent.getAction();

                // Only process key up action, otherwise this listener will be triggered twice because of key down action.
                if(action == KeyEvent.ACTION_UP)
                {
                    processusernameButtonByTextLength();
                }
                return false;
            }
        });

        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_phone.setClickable(true);
                et_phone.setEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_phone.setClickable(true);
                et_phone.setEnabled(true);
                btn_okphone.setVisibility(btn_okphone.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                processphoneButtonByTextLength();
            }
        });


        et_phone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                // Get key action, up or down.
                int action = keyEvent.getAction();

                // Only process key up action, otherwise this listener will be triggered twice because of key down action.
                if(action == KeyEvent.ACTION_UP)
                {
                    processphoneButtonByTextLength();
                }
                return false;
            }
        });

        et_retypepassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               btn_okchangepass.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btn_okchangepass.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                processphoneButtonByTextLength();
            }
        });


        et_phone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                // Get key action, up or down.
                int action = keyEvent.getAction();

                // Only process key up action, otherwise this listener will be triggered twice because of key down action.
                if(action == KeyEvent.ACTION_UP)
                {
                    processphoneButtonByTextLength();
                }
                return false;
            }
        });
    }

    private void processaddressoneButtonByTextLength()
    {
        String inputText = et_address1.getText().toString();
        if(inputText.length() > 50)
        {
          btn_okaddress1.setTextColor(getResources().getColor(android.R.color.white));
            btn_okaddress1.setVisibility(btn_okaddress1.VISIBLE);
        }
        else
        {
            btn_okaddress1.setEnabled(false);
            btn_okaddress1.setVisibility(btn_okaddress1.GONE);
        }
    }

    private void processaddresstwoButtonByTextLength()
    {
        String inputText = et_address2.getText().toString();
        if(inputText.length() > 50)
        {
            btn_okaddress2.setTextColor(getResources().getColor(android.R.color.white));
            btn_okaddress2.setEnabled(true);
            btn_okaddress2.setVisibility(btn_okaddress2.VISIBLE);
        }else
        {
            btn_okaddress2.setEnabled(false);
            btn_okaddress1.setVisibility(btn_okaddress2.GONE);
        }
    }

    private void processusernameButtonByTextLength()
    {
        String inputText = et_username.getText().toString();

            btn_okusername.setTextColor(getResources().getColor(android.R.color.white));
            btn_okusername.setEnabled(true);
            btn_okusername.setVisibility(btn_okusername.VISIBLE);
    }

    private void processphoneButtonByTextLength()
    {
        String inputText = et_phone.getText().toString();

            btn_okphone.setTextColor(getResources().getColor(android.R.color.white));
            btn_okphone.setEnabled(true);
            btn_okphone.setVisibility(btn_okphone.VISIBLE);

    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == 1888) {

                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Bitmap convertimage=getResizedBitmap(photo,600);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                convertimage.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byteArray = stream.toByteArray();
                img_profilepic.setImageBitmap(convertimage);

            }

            } else if (requestCode == 2) {



                imageUri = data.getData();
                path=imageUri.getPath();
                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                Bitmap convertimage=getResizedBitmap(selectedImage,600);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                convertimage.compress(Bitmap.CompressFormat.PNG, 50, stream);
                byteArray = stream.toByteArray();
                img_profilepic.setImageBitmap(convertimage);
                *//*Log.w("path of image from gallery......******************.........", picturePath+"");*//*

            }

        }




    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private void selectImage() {



        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfileActivity.this);

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }

                else if (options[item].equals("Choose from Gallery"))

                {

                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(intent, 2);



                }

                else if (options[item].equals("Cancel")) {

                    dialog.dismiss();

                }

            }

        });

        builder.show();

    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    public  boolean isGalleryPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG,"Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }




    }

    private boolean validate(){
        if(et_address1.getText().toString().trim().equals("")) {
            et_address1.setError("You cannot leave the field blank");
            return false;
        }
        *//*else if(et_address2.getText().toString().trim().equals("")) {
            et_address2.setError("You cannot leave the field blank");
            clickcount=0;
            return false;
        }*//*
        else
            return true;
    }
}*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MyProfileActivity.this.finish();
    }
}