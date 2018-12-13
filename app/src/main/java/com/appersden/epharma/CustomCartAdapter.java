package com.appersden.epharma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by azeR on 12/8/2018.
 */

public class CustomCartAdapter extends BaseAdapter {

    Context context;
    List<Cart> cartList;

    public CustomCartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.custom_cart_list,parent,false);

        }
        TextView tv_name=(TextView)convertView.findViewById(R.id.tv_medicinenamecart);
        TextView tv_company=(TextView)convertView.findViewById(R.id.tv_medicinecompanycart);
        TextView tv_power=(TextView)convertView.findViewById(R.id.tv_powermedicinecart);
        TextView tv_first=(TextView)convertView.findViewById(R.id.tv_firstnumofmedicinemultiplycart);
        TextView tv_total=(TextView)convertView.findViewById(R.id.tv_totalnumofmedicinemultiplycart);
        TextView tv_price=(TextView)convertView.findViewById(R.id.tv_medicinepricecart);
        tv_name.setText(cartList.get(position).getName());
        tv_company.setText(cartList.get(position).getCompany());
        tv_power.setText(String.valueOf(cartList.get(position).getPower()));
        tv_first.setText(String.valueOf(cartList.get(position).getFirst()));
        tv_total.setText(String.valueOf(cartList.get(position).getTotalmultiply()));
        tv_price.setText(String.valueOf(cartList.get(position).getPrice()));
        return convertView;
    }
}
