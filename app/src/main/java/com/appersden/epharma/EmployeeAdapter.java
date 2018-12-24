package com.appersden.epharma;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by azeR on 12/7/2018.
 */

public class EmployeeAdapter extends BaseAdapter implements Filterable {

    public Context context;
    public ArrayList<Medicine> employeeArrayList;
    public ArrayList<Medicine> orig;

    public EmployeeAdapter(Context context, ArrayList<Medicine> employeeArrayList) {
        super();
        this.context = context;
        this.employeeArrayList = employeeArrayList;
    }


    public class EmployeeHolder
    {
        ImageView capsule;
        TextView name;
        TextView age;
    }

    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Medicine> results = new ArrayList<Medicine>();
                if (orig == null)
                    orig = employeeArrayList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final Medicine g : orig) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString().toLowerCase()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                employeeArrayList = (ArrayList<Medicine>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return employeeArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return employeeArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EmployeeHolder holder;
        if(convertView==null)
        {
            convertView= LayoutInflater.from(context).inflate(R.layout.custom_medicine_list, parent, false);
            holder=new EmployeeHolder();
            holder.capsule=(ImageView)convertView.findViewById(R.id.img_capsuleicon);
            holder.name=(TextView) convertView.findViewById(R.id.tv_medicinename);
            holder.age=(TextView) convertView.findViewById(R.id.tv_medicinecompany);
            convertView.setTag(holder);
        }
        else
        {
            holder=(EmployeeHolder) convertView.getTag();
        }
        holder.capsule.setImageResource(employeeArrayList.get(position).getImage());
        holder.name.setText(employeeArrayList.get(position).getName());
        holder.age.setText(employeeArrayList.get(position).getCompany());

        return convertView;

    }

}


