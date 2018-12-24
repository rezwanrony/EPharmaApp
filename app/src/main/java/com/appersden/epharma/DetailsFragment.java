package com.appersden.epharma;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class DetailsFragment extends Fragment {

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
    DatabaseHandler db;
    EditText et_username, et_phone, et_address1, et_address2, et_address3, et_oldpassword, et_newpassword, et_retypepassword,et_email;
    Button btn_okusername, btn_okphone, btn_okgender, btn_okaddress1, btn_okaddress2, btn_okaddress3, btn_addanothertext, btn_okchangepass;
    ImageView img_editusername, img_editphone, img_editaddresss1, img_editaddresss2, img_editgender,img_editphoto,img_editchangepass;
    LinearLayout lladdresstwo, lladdressthree;
    String TAG = "permission";
    private int RESULT_LOAD_IMG = 1;
    SessionManager session;
    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    String gendertexts="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        et_username = (EditText) rootView.findViewById(R.id.et_usernamemyprofile);
        et_phone = (EditText) rootView.findViewById(R.id.et_phonemyprofile);
        session = new SessionManager(getActivity());
        et_oldpassword = (EditText) rootView.findViewById(R.id.et_oldpasswordprofile);
        et_newpassword = (EditText) rootView.findViewById(R.id.et_newpassprofile);
        et_retypepassword = (EditText) rootView.findViewById(R.id.et_confpassprofile);
        et_email=(EditText)rootView.findViewById(R.id.et_emailmyprofile );
        img_profilepic=(CircleImageView)rootView.findViewById(R.id.img_profilepicdetails);
        img_editusername = (ImageView) rootView.findViewById(R.id.img_editemailmyprofile);
        img_editphone = (ImageView) rootView.findViewById(R.id.img_editphonemyprofile);
        img_editgender = (ImageView) rootView.findViewById(R.id.img_editgendermyprofile);
        gender = (Spinner)rootView.findViewById(R.id.spinnergendermyprofile);
        img_editphoto=(ImageView)rootView.findViewById(R.id.img_editprofilemypro);
        img_editchangepass=(ImageView)rootView.findViewById(R.id.img_editchangepassmyprofile);
        db = new DatabaseHandler(getActivity());

        List<String> flower = new ArrayList<>();
        flower.add("Male");
        flower.add("Female");
        flower.add("Others");

        // Initialize an array adapter
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,flower) {
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





        img_editusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        if (session.isLoggedIn()) {
            Map map = new HashMap();
            map = db.getUserDetails();
            String username = map.get("username").toString();
            String email = map.get("email").toString();
            String phone = map.get("phone").toString();
            gendertexts = map.get("gender").toString();
            et_username.setText(username);
            et_email.setText(email);
            et_phone.setText(phone);
            if (gendertexts.equals("Female")) {
                img_profilepic.setImageDrawable(getResources().getDrawable(R.drawable.propicfemale));
            }
            else {
                img_profilepic.setImageDrawable(getResources().getDrawable(R.drawable.propicmale));
            }


            img_editusername.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    et_email.setEnabled(true);
                    et_email.setClickable(true);
                }
            });



            img_editphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    et_phone.setEnabled(true);
                    et_phone.setClickable(true);
                }
            });

            img_editgender.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gender.setEnabled(true);
                    gender.setClickable(true);
                }
            });


            img_editphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectImage();
                }
            });

            img_editchangepass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    et_retypepassword.setEnabled(true);
                    et_retypepassword.setClickable(true);
                }
            });
        }

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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
                imageStream = getActivity().getContentResolver().openInputStream(imageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            Bitmap convertimage=getResizedBitmap(selectedImage,600);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            convertimage.compress(Bitmap.CompressFormat.PNG, 50, stream);
            byteArray = stream.toByteArray();
            img_profilepic.setImageBitmap(convertimage);
            //*Log.w("path of image from gallery......******************.........", picturePath+"");*//*

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

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Add Photo!");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo"))

                {
                    if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
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
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
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
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG,"Permission is granted");
                return true;
            } else {

                Log.v(TAG,"Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getActivity()
                        , "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }




    }
}
