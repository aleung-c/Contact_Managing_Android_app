package com.example.aleung_c.ft_hangouts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends ArrayAdapter{

    private List<Contact> objects;

    public MyAdapter(Context context, int resource, List<Contact> objects) {
        super(context, resource, objects);
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // assign the view we are converting to a local variable
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_twofields, null);
        }
        Contact i = objects.get(position);
        if (i != null) {
            TextView tv1 = (TextView) v.findViewById(R.id.listv_home_f1);
            TextView tv2 = (TextView) v.findViewById(R.id.listv_home_f2);
            if (tv1 != null){
                tv1.setText(i.getName());
            }
            if (tv1 != null){
                tv2.setText(i.getPhonenb());
            }
        }
        return v;
    }
}