package com.infinity.kich.Leave.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.android.volley.RequestQueue;
import com.infinity.kich.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pragna on 16-4-2019.
 */

public class SpinnerSimpleAdapter extends BaseAdapter {


    private Context ctx;
    ViewHolder holder = null;
    public FragmentManager f_manager;
    private LayoutInflater inflater;
    SharedPreferences prefs;
    View view;
    Activity a;
    Boolean b;
    ArrayList<String> type;


    public SpinnerSimpleAdapter(Context ctx, ArrayList<String> type) {

        this.ctx = ctx;
        this.b = b;
        inflater = LayoutInflater.from(this.ctx);
        this.type = new ArrayList<>();
        this.type = type;


    }

    @Override
    public int getCount() {
        // return customBenifitList.size();
        return type.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
        //  return customBenifitList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    private class ViewHolder {

        CheckBox itemCheckBox;

        private TextView tv_tv1;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.spinner_simpleadapter, null);
            holder = new ViewHolder();
            holder.tv_tv1 = (TextView) view.findViewById(R.id.text1);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_tv1.setText(type.get(position) + "");


        return view;
    }


}