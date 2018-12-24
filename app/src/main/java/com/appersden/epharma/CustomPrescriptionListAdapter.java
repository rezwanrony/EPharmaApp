package com.appersden.epharma;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zhihu.matisse.Matisse;

import java.util.ArrayList;

public class CustomPrescriptionListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Uri>uriArrayList;

    public CustomPrescriptionListAdapter(Context context, ArrayList<Uri> uriArrayList) {
        this.context = context;
        this.uriArrayList = uriArrayList;
    }

    @Override
    public int getCount() {
        return uriArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return uriArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.custom_prescription,viewGroup,false);

        }
        ImageView img_background=(ImageView)view.findViewById(R.id.img_prescription);
        Uri imageUri = uriArrayList.get(0);
        img_background.setImageURI(imageUri);
        return view;
    }
}
