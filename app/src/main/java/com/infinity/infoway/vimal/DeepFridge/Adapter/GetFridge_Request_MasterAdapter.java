package com.infinity.infoway.vimal.DeepFridge.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.infinity.infoway.vimal.DeepFridge.Pojo.GetFridge_Request_MasterPojo;
import com.infinity.infoway.vimal.R;

import java.util.ArrayList;

/**
 * Created by Pragna on 06-6-2021. for fridge listing adapter
 */

public class GetFridge_Request_MasterAdapter extends BaseAdapter {


    private Context ctx;
    ViewHolder holder = null;
    public FragmentManager f_manager;
    private LayoutInflater inflater;
    SharedPreferences prefs;
    View view;
    Activity a;
    Boolean b;
    GetFridge_Request_MasterPojo pojo;


    public GetFridge_Request_MasterAdapter(Context ctx, GetFridge_Request_MasterPojo pojo) {

        this.ctx = ctx;
        this.b = b;
        inflater = LayoutInflater.from(this.ctx);
        //this.pojo = new GetFridge_Request_MasterPojo.RECORDSBean();
        this.pojo = pojo;


    }

    @Override
    public int getCount() {
        // return customBenifitList.size();
        return pojo.RECORDS.size();
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

        private TextView tv_1,tv_2,tv_3,tv_4,tv_5,tv_6;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.fridge_listing_adapter, null);
            holder = new ViewHolder();
            holder.tv_1 = (TextView) view.findViewById(R.id.tv_1);
            holder.tv_2 = (TextView) view.findViewById(R.id.tv_2);
            holder.tv_3 = (TextView) view.findViewById(R.id.tv_3);
            holder.tv_4 = (TextView) view.findViewById(R.id.tv_4);
            holder.tv_5 = (TextView) view.findViewById(R.id.tv_5);
            holder.tv_6 = (TextView) view.findViewById(R.id.tv_6);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_1.setText(pojo.RECORDS.get(position).frm_no + "");
        holder.tv_2.setText(pojo.RECORDS.get(position).frm_date + "");
        holder.tv_3.setText(pojo.RECORDS.get(position).cus_name + "");
        holder.tv_4.setText(pojo.RECORDS.get(position).frm_retailer_name + "");
        holder.tv_5.setText(pojo.RECORDS.get(position).frm_company_name + "");
        holder.tv_6.setText(pojo.RECORDS.get(position).Fridge_Size + "");


        return view;
    }


}